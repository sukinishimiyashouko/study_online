package com.wbu.service;

import java.util.Map;

/**
 * @auther 11852
 * @create 2023/9/9
 */
public interface WXPayService {
    Map<String, String> createJsapi(String orderNo);
    //根据订单号调用微信接口查询支付状态
    Map<String, String> queryPayStatus(String orderNo);
}
