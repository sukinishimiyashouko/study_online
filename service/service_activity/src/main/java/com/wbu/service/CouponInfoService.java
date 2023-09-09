package com.wbu.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.model.activity.CouponInfo;
import com.wbu.model.activity.CouponUse;
import com.wbu.vo.activity.CouponUseQueryVo;

/**
* @author 11852
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service
* @createDate 2023-09-07 15:23:57
*/
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> couponUsePage, CouponUseQueryVo couponUseQueryVo);

    void updateCouponInfoUseStatus(Long couponUseId, Long orderId);
}
