package com.wbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.model.order.OrderDetail;
import com.wbu.service.OrderDetailService;
import com.wbu.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 11852
* @description 针对表【order_detail(订单明细 订单明细)】的数据库操作Service实现
* @createDate 2023-09-05 12:35:06
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




