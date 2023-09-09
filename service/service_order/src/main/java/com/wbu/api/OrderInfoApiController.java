package com.wbu.api;

import com.wbu.result.Result;
import com.wbu.service.OrderInfoService;
import com.wbu.vo.order.OrderFormVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 11852
 * @create 2023/9/9
 */
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoApiController {
    private final OrderInfoService orderInfoService;

    public OrderInfoApiController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }
    //生成订单
    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody OrderFormVo orderFormVo){
        Long orderId = orderInfoService.submitOrder(orderFormVo);
        return Result.success(orderId);
    }

}
