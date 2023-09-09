package com.wbu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 11852
 * @create 2023/9/1
 */
@Configuration
@MapperScan("com.wbu.mapper")
@ComponentScan(basePackages = "com.wbu")
public class VodConfig {

}
