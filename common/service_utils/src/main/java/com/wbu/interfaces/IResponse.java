package com.wbu.interfaces;

/**
 * @auther 11852
 * @create 2023/9/2
 */
public interface IResponse {
    /**
     * 获取状态码
     * @return code
     */
    int getCode();

    /**
     * 获取消息
     * @return message
     */
    String getMessage();
}
