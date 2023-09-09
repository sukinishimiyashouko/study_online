package com.wbu.exception;

import com.wbu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @auther 11852
 * @create 2023/9/2
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获Controller中抛出的不同类型的异常，从而达到异常全局处理的目的
     */
    //自定义异常
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleBusinessException(BusinessException e){
        log.info(e.getMessage());
        return new Result<>(500,e.getMessage(),null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        return new Result<>(500,fieldError.getDefaultMessage(),null);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleRuntimeException(RuntimeException e){
        return new Result<>(500,e.getMessage(),null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleException(MethodArgumentNotValidException e){
        return new Result<>(500,e.getMessage(),null);
    }
}
