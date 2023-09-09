package com.wbu.client.user;

import com.wbu.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther 11852
 * @create 2023/9/8
 */
@FeignClient(value = "service-user")
public interface UserInfoClient {

    @GetMapping("/admin/user/userInfo/inner/getById/{id}")
    UserInfo getById(@PathVariable Long id);

}
