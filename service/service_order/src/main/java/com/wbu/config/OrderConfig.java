package com.wbu.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 11852
 * @create 2023/9/5
 */
@Configuration
@MapperScan("com.wbu.mapper")
public class OrderConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        return new PaginationInnerInterceptor();
    }
}
