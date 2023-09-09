package com.wbu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.model.order.OrderInfo;
import com.wbu.result.Result;
import com.wbu.service.OrderInfoService;
import com.wbu.vo.order.OrderInfoQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @auther 11852
 * @create 2023/9/5
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    private final OrderInfoService orderInfoService;

    public OrderInfoController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }
    //订单列表
    @GetMapping("/{page}/{limit}")
    public Result listOrder(@PathVariable Long page,
                            @PathVariable Long limit,
                            OrderInfoQueryVo orderInfoQueryVo){
        //创建配置对象
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        Map<String,Object> map = orderInfoService.selectOrderInfoPage(pageParam,orderInfoQueryVo);
        return Result.success(map);
    }
}
