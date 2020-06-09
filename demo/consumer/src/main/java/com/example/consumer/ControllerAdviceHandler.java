package com.example.consumer;

import cn.minsin.feign.exception.BaseRemoteCallException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 22:55
 */
@ControllerAdvice
public class ControllerAdviceHandler {


    @ExceptionHandler({BaseRemoteCallException.class})
    @ResponseBody
    public String hh(BaseRemoteCallException exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }
}
