package com.wbu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther 11852
 * @create 2023/9/7
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.wbu")
public class ServiceActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceActivityApplication.class,args);
    }
}
