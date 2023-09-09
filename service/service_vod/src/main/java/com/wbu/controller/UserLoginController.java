package com.wbu.controller;

import com.wbu.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther 11852
 * @create 2023/9/3
 */
@RestController
@RequestMapping("/admin/vod/user")
public class UserLoginController {
    //login
    @PostMapping("/login")
    public Result login(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.success(map);
    }

    @GetMapping("/info")
    public Result info(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return Result.success(map);
    }
}
