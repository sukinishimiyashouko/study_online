package com.wbu.controller;

import com.alibaba.fastjson.JSONObject;
import com.wbu.exception.BusinessException;
import com.wbu.model.wechat.Menu;
import com.wbu.result.Result;
import com.wbu.service.MenuService;
import com.wbu.utils.ConstantPropertiesUtil;
import com.wbu.utils.HttpClientUtils;
import com.wbu.vo.wechat.MenuVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther 11852
 * @create 2023/9/8
 */
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "获取")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        Menu menu = menuService.getById(id);
        return Result.success(menu);
    }
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu){
        menuService.save(menu);
        return Result.success(null);
    }
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result updateById(@RequestBody Menu menu){
        menuService.updateById(menu);
        return Result.success(null);
    }
    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result batchRemove(@RequestBody List<Long> idList){
        menuService.removeByIds(idList);
        return Result.success(null);
    }
    //获取所有的菜单，按照一级和二级菜单封装
    @GetMapping("/findMenuInfo")
    public Result findMenuInfo(){
        List<MenuVo> list = menuService.findMenuInfo();
        return Result.success(list);
    }
    //获取所有以及菜单
    @GetMapping("/findOneMenuInfo")
    public Result findOneMenuInfo(){
        List<Menu> list = menuService.findMenuOneInfo();
        return Result.success(list);
    }
    //获取access_token
    @GetMapping("/getAccessToken")
    public Result getAccessToken(){
        //拼接请求地址
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://api.weixin.qq.com/cgi-bin/token");
        buffer.append("?grant_type=client_credential");
        buffer.append("&appid=%s");
        buffer.append("&secret=%s");
        //设置路径参数
        String url = String.format(buffer.toString(),
                ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        //get请求
        try {
            String tokenString = HttpClientUtils.get(url);
            //获取access_token
            JSONObject jsonObject = JSONObject.parseObject(tokenString);
            String access_token = jsonObject.getString("access_token");
            return Result.success(access_token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取access_token失败");
        }
    }

    //同步菜单方法
    @GetMapping("/syncMenu")
    public Result createMenu(){
        menuService.syncMenu();
        return Result.success(null);
    }

    //公众号菜单删除
    @DeleteMapping("/removeMenu")
    public Result removeMenu(){
        menuService.removeMenu();
        return Result.success(null);
    }
}
