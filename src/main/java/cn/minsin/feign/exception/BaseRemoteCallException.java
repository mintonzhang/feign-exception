package cn.minsin.feign.exception;

import cn.minsin.feign.model.ExceptionChain;
import lombok.NonNull;

import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/6/9 16:57
 */
public abstract class BaseRemoteCallException extends RuntimeException {

    public BaseRemoteCallException() {
    }

    public BaseRemoteCallException(String message) {
        super(message);
    }

    public BaseRemoteCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public abstract BaseRemoteCallException throwException(String message);

    public abstract BaseRemoteCallException throwException(String message, @NonNull List<ExceptionChain> exceptionChains);

    public abstract BaseRemoteCallException throwException(String message, Throwable cause);


    /**
     * 判断异常是否为原始异常的子类
     */
    public abstract boolean isInstanceOf(Class<? extends Throwable> exception);

}
