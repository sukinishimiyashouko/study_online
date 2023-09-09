package com.wbu.api;

import com.wbu.result.Result;
import com.wbu.service.WXPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @auther 11852
 * @create 2023/9/9
 */
@Api(tags = "微信支付接口")
@RestController
@RequestMapping("/api/order/wxPay")
public class WXPayController {
    private final WXPayService wxPayService;

    public WXPayController(WXPayService wxPayService) {
        this.wxPayService = wxPayService;
    }
    @ApiOperation(value = "下单 小程序支付")
    @GetMapping("/createJsapi/{orderNo}")
    public Result createJsapi(@ApiParam(name = "orderNo",value = "订单NO",required = true)
                              @PathVariable String orderNo){
        Map<String,String> map = wxPayService.createJsapi(orderNo);
        return Result.success(map);
    }
}
