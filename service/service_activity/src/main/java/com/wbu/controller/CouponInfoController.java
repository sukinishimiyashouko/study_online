package com.wbu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.model.activity.CouponInfo;
import com.wbu.model.activity.CouponUse;
import com.wbu.result.Result;
import com.wbu.service.CouponInfoService;
import com.wbu.vo.activity.CouponUseQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther 11852
 * @create 2023/9/7
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {
    private final CouponInfoService couponInfoService;

    public CouponInfoController(CouponInfoService couponInfoService) {
        this.couponInfoService = couponInfoService;
    }
    @ApiOperation(value = "获取优惠券")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id){
        CouponInfo infoServiceById = couponInfoService.getById(id);
        return Result.success(infoServiceById);
    }

    @ApiOperation(value = "新增优惠券")
    @PostMapping("/save")
    public Result save(@RequestBody CouponInfo couponInfo){
        couponInfoService.save(couponInfo);
        return Result.success(null);
    }

    @ApiOperation(value = "修改优惠券")
    @PutMapping("/update")
    public Result updateById(@RequestBody CouponInfo couponInfo){
        couponInfoService.updateById(couponInfo);
        return Result.success(null);
    }

    @ApiOperation("优惠券删除")
    @DeleteMapping("/remove/{id}")
    public Result batchRemove(@PathVariable String id){
        couponInfoService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> id){
        couponInfoService.removeByIds(id);
        return Result.success(null);
    }

    /**
     * 优惠券分页列表
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@ApiParam(name = "page",value = "当前页码",required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit",value = "每页记录数",required = true)
                        @PathVariable Long limit){
        Page<CouponInfo> couponInfoPage = new Page<>(page, limit);
        IPage<CouponInfo> pageModel = couponInfoService.page(couponInfoPage);
        return Result.success(pageModel);
    }

    /**
     * 获取已经使用优惠券列表(条件查询分页)
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/couponUse/{page}/{limit}")
    public Result index(@ApiParam(name = "page",value = "当前页码",required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit",value = "每页记录数",required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "couponUseVo",value = "查询对象",required = true)
                        CouponUseQueryVo couponUseQueryVo){
        Page<CouponUse> couponUsePage = new Page<>(page,limit);
        IPage<CouponUse> pageModel = couponInfoService.selectCouponUsePage(couponUsePage,couponUseQueryVo);
        return Result.success(pageModel);
    }
}
