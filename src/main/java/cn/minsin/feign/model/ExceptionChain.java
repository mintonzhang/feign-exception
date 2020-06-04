package cn.minsin.feign.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: minton.zhang
 * @since: 2020/6/4 15:31
 */
@Getter
@Setter
public class ExceptionChain {

    /**
     * happened timestamp
     */
    private Date timestamp;

    /**
     * happened exceptionClass
     */
    private String exceptionClass;

    /**
     * message of exception
     */
    private String message;

    /**
     * the feign client path url
     */
    private String path;
}
