package com.wbu.result;

import com.wbu.interfaces.IResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @auther 11852
 * @create 2023/9/2
 */
@Data
@AllArgsConstructor
public class Result<T> implements IResponse{
    private final int code;//状态码
    private final String message;//状态信息
    private final T data;//返回数据
    //成功的方法
    public static <T> Result<T> success(){
        return success(null);
    }
    public static <T> Result<T> success(T data){
        return new Result(20000,"success",data);
    }
    //失败的方法
    public static <T> Result<T> fail(){
        return fail(null);
    }
    public static <T> Result<T> fail(T data){
        return new Result<>(500,"fail",data);
    }

    @Override
    public int getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
