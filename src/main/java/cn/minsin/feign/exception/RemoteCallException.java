package cn.minsin.feign.exception;

/**
 * feign远程调用异常
 *
 * @author: minton.zhang
 * @since: 2020/6/3 22:31
 */
public class RemoteCallException extends RuntimeException {
    public RemoteCallException(String message) {
        super(message);
    }

    public RemoteCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
