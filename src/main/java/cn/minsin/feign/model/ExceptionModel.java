package cn.minsin.feign.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 22:04
 */
@Getter
@Setter
public class ExceptionModel {

    /**
     * 发生时间
     */
    private Date timestamp;

    /**
     * 相应状态
     */
    private Integer status;

    /**
     * 错误原因
     */
    private String error;

    /**
     * exception中包含的信息
     */
    private String message;

    /**
     * 出错的路径
     */
    private String path;

    /**
     * 抛出的异常 全称 java.lang.RuntimeException
     */
    private String throwExceptionClass;

}
