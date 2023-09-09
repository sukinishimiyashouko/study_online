package com.wbu.api;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wbu.model.activity.CouponInfo;
import com.wbu.service.CouponInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 11852
 * @create 2023/9/9
 */
@Api(tags = "优惠券接口")
@RestController
@RequestMapping("/api/activity/couponInfo")
public class CouponInfoApiController {

    private final CouponInfoService couponInfoService;

    public CouponInfoApiController(CouponInfoService couponInfoService) {
        this.couponInfoService = couponInfoService;
    }
    @ApiOperation(value = "获取优惠券")
    @GetMapping("/inner/getById/{couponId}")
    public CouponInfo getById(@PathVariable Long couponId){
        CouponInfo couponInfo = couponInfoService.getById(couponId);
        return couponInfo;
    }
    @ApiOperation("更新优惠券使用状态")
    @GetMapping("/inner/updateCouponInfoUseStatus/{couponUseId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(@PathVariable Long couponUseId,
                                             @PathVariable Long orderId){
        couponInfoService.updateCouponInfoUseStatus(couponUseId,orderId);
        return true;
    }
}
