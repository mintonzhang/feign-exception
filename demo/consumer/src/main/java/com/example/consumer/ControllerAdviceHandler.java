//package com.example.consumer;
//
//import cn.minsin.feign.exception.RemoteCallException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author: minton.zhang
// * @since: 2020/6/3 22:55
// */
//@ControllerAdvice
//public class ControllerAdviceHandler {
//
//
//    @ExceptionHandler({RemoteCallException.class})
//    @ResponseBody
//    public String hh(RemoteCallException exception) {
//        exception.printStackTrace();
//        return exception.getRawMessage();
//    }
//}
