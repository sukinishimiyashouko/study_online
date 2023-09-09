package com.wbu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.model.order.OrderInfo;
import com.wbu.vo.order.OrderFormVo;
import com.wbu.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
* @author 11852
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service
* @createDate 2023-09-05 12:34:50
*/
public interface OrderInfoService extends IService<OrderInfo> {

    Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    Long submitOrder(OrderFormVo orderFormVo);
}
