package com.wbu.controller;

import com.wbu.model.user.UserInfo;
import com.wbu.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 11852
 * @create 2023/9/8
 */
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
    @ApiOperation(value = "获取")
    @GetMapping("/inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id){
        UserInfo userInfo = userInfoService.getById(id);
        return userInfo;
    }
}
