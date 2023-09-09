package com.wbu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.client.user.UserInfoClient;
import com.wbu.model.activity.CouponInfo;
import com.wbu.model.activity.CouponUse;
import com.wbu.model.user.UserInfo;
import com.wbu.service.CouponInfoService;
import com.wbu.mapper.CouponInfoMapper;
import com.wbu.service.CouponUseService;
import com.wbu.vo.activity.CouponUseQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
* @author 11852
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service实现
* @createDate 2023-09-07 15:23:57
*/
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo>
    implements CouponInfoService{

    private final CouponUseService couponUseService;
    private final UserInfoClient userInfoFeignClient;

    public CouponInfoServiceImpl(CouponUseService couponUseService, UserInfoClient userInfoFeignClient) {
        this.couponUseService = couponUseService;
        this.userInfoFeignClient = userInfoFeignClient;
    }

    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> couponUsePage, CouponUseQueryVo couponUseQueryVo) {
        /**
         * 获取已经使用的优惠券列表
         */
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();

        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(couponId)) {
            wrapper.eq("coupon_id",couponId);
        }
        if(!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq("coupon_status",couponStatus);
        }
        if(!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge("get_time",getTimeBegin);
        }
        if(!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le("get_time",getTimeEnd);
        }
        //调用方法条件分页查询
        IPage<CouponUse> pageModel = couponUseService.page(couponUsePage, wrapper);
        List<CouponUse> couponUseList = pageModel.getRecords();
        //遍历
        couponUseList.forEach(this::getUserInfoById);
        return pageModel;
    }

    @Override
    public void updateCouponInfoUseStatus(Long couponUseId, Long orderId) {
        CouponUse couponUse = new CouponUse();
        couponUse.setId(couponUseId);
        couponUse.setOrderId(orderId);
        couponUse.setCouponStatus("1");
        couponUse.setUsingTime(new Date());
        couponUseService.updateById(couponUse);
    }

    //根据用户id，通过远程调用得到用户信息
    private CouponUse getUserInfoById(CouponUse couponUse) {
        //获取用户id
        Long userId = couponUse.getUserId();
        if(!StringUtils.isEmpty(userId)) {
            //远程调用
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if(userInfo != null) {
                couponUse.getParam().put("nickName",userInfo.getNickName());
                couponUse.getParam().put("phone",userInfo.getPhone());
            }
        }
        return couponUse;
    }
}




