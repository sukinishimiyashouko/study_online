package com.wbu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 11852
 * @create 2023/9/8
 */
@Configuration
@MapperScan("com.wbu.mapper")
public class UserConfig {

}
