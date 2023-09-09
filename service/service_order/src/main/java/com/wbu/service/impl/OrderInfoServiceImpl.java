package com.wbu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.client.CouponInfoFeignClient;
import com.wbu.client.course.CourseFeignClient;
import com.wbu.client.user.UserInfoClient;
import com.wbu.model.activity.CouponInfo;
import com.wbu.model.order.OrderDetail;
import com.wbu.model.order.OrderInfo;
import com.wbu.model.user.UserInfo;
import com.wbu.model.vod.Course;
import com.wbu.service.OrderDetailService;
import com.wbu.service.OrderInfoService;
import com.wbu.mapper.OrderInfoMapper;
import com.wbu.utils.AuthContextHolder;
import com.wbu.utils.OrderNoUtils;
import com.wbu.vo.order.OrderFormVo;
import com.wbu.vo.order.OrderInfoQueryVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author 11852
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service实现
* @createDate 2023-09-05 12:34:50
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
    implements OrderInfoService{

    private final OrderDetailService orderDetailService;
    private final CourseFeignClient courseFeignClient;
    private final CouponInfoFeignClient couponInfoFeignClient;
    private final UserInfoClient userInfoClient;
    public OrderInfoServiceImpl(OrderDetailService orderDetailService, CourseFeignClient courseFeignClient, CouponInfoFeignClient couponInfoFeignClient, UserInfoClient userInfoClient) {
        this.orderDetailService = orderDetailService;
        this.courseFeignClient = courseFeignClient;
        this.couponInfoFeignClient = couponInfoFeignClient;
        this.userInfoClient = userInfoClient;
    }

    @Override
    public Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {
        //orderInfoQueryVo获取查询条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();

        //判断条件值是否为空，不为空，进行条件封装
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if(!Objects.isNull(orderStatus)) {
            wrapper.eq("order_status",orderStatus);
        }

        if(!Objects.isNull(userId)) {
            wrapper.eq("user_id",userId);
        }
        if(!Objects.isNull(outTradeNo)) {
            wrapper.eq("out_trade_no",outTradeNo);
        }
        if(!Objects.isNull(phone)) {
            wrapper.eq("phone",phone);
        }
        if(!Objects.isNull(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!Objects.isNull(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用实现条件分页查询
        Page<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long pageCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords();
        //订单里面包含详情内容，封装详情数据，根据订单id查询详情
        records.stream().forEach(this::getOrderDetail);

        //所有需要数据封装map集合，最终返回
        Map<String,Object> map = new HashMap<>();
        map.put("total",totalCount);
        map.put("pageCount",pageCount);
        map.put("records",records);
        return map;
    }

    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
        //获取生成订单条件值
        Long couponId = orderFormVo.getCouponId();
        Long courseId = orderFormVo.getCourseId();
        Long userId = AuthContextHolder.getUserId();

        //判断当前用户是否已经生成订单
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getCourseId,courseId);
        wrapper.eq(OrderDetail::getUserId,userId);
        OrderDetail orderDetailExist = orderDetailService.getOne(wrapper);
        if (orderDetailExist!=null){
            return orderDetailExist.getId();
        }
        //根据课程id查询出课程信息
        Course course = courseFeignClient.getById(courseId);
        if (Objects.isNull(course)){
            throw new RuntimeException("课程不存在");
        }
        //根据用户id查询出用户信息
        UserInfo userInfo = userInfoClient.getById(userId);
        if (Objects.isNull(userInfo)){
            throw new RuntimeException("用户不存在");
        }
        //根据优惠券id查询优惠券信息
        BigDecimal couponReduce = new BigDecimal(0);
        if (couponId!=null){
            CouponInfo couponInfo = couponInfoFeignClient.getById(couponId);
            couponReduce = couponInfo.getAmount();
        }
        //6.1 封装数据到OrderInfo对象里面，添加订单基本信息表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setPhone(userInfo.getPhone());
        orderInfo.setProvince(userInfo.getProvince());
        orderInfo.setOriginAmount(course.getPrice());
        orderInfo.setCouponReduce(couponReduce);
        orderInfo.setFinalAmount(orderInfo.getOriginAmount().subtract(orderInfo.getCouponReduce()));
        orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
        orderInfo.setTradeBody(course.getTitle());
        orderInfo.setOrderStatus("0");
        baseMapper.insert(orderInfo);

        //6.2 封装数据到OrderDetail对象里面，添加订单详情信息表
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        orderDetail.setUserId(userId);
        orderDetail.setCourseId(courseId);
        orderDetail.setCourseName(course.getTitle());
        orderDetail.setCover(course.getCover());
        orderDetail.setOriginAmount(course.getPrice());
        orderDetail.setCouponReduce(new BigDecimal(0));
        orderDetail.setFinalAmount(orderDetail.getOriginAmount().subtract(orderDetail.getCouponReduce()));
        orderDetailService.save(orderDetail);

        //7 更新优惠券状态
        if(null != orderFormVo.getCouponUseId()) {
            couponInfoFeignClient.updateCouponInfoUseStatus(orderFormVo.getCouponUseId(), orderInfo.getId());
        }
        //8 返回订单id
        return orderInfo.getId();
    }

    //查询订单详情数据
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        //订单id
        Long id = orderInfo.getId();
        //查询订单详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if(orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName",courseName);
        }
        return orderInfo;
    }
}




