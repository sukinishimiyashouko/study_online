package com.wbu.client;

import com.wbu.model.activity.CouponInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther 11852
 * @create 2023/9/9
 */
@FeignClient(value = "server-activity")
public interface CouponInfoFeignClient {

    @ApiOperation(value = "获取优惠券")
    @GetMapping(value = "/api/activity/couponInfo/inner/getById/{couponId}")
    CouponInfo getById(@PathVariable("couponId") Long couponId);

    /**
     * 更新优惠券使用状态
     */
    @GetMapping(value = "/api/activity/couponInfo/inner/updateCouponInfoUseStatus/{couponUseId}/{orderId}")
    Boolean updateCouponInfoUseStatus(@PathVariable("couponUseId") Long couponUseId, @PathVariable("orderId") Long orderId);
}
