package com.wbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.model.user.UserInfo;
import com.wbu.service.UserInfoService;
import com.wbu.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 11852
* @description 针对表【user_info(用户表)】的数据库操作Service实现
* @createDate 2023-09-08 10:48:10
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




