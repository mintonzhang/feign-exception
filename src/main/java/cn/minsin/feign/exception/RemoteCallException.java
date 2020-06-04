package cn.minsin.feign.exception;

import cn.minsin.feign.model.ExceptionChain;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.util.CollectionUtils;

import java.io.PrintStream;
import java.util.List;

/**
 * feign远程调用异常
 *
 * @author: minton.zhang
 * @since: 2020/6/3 22:31
 */
@Slf4j
public class RemoteCallException extends RuntimeException {

    @Getter
    private List<ExceptionChain> exceptionChains;


    public RemoteCallException(String message) {
        super(message);
    }

    public RemoteCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteCallException(String message, List<ExceptionChain> exceptionChains) {
        super(message);
        this.exceptionChains = exceptionChains;
    }

    /**
     * 获取原始异常信息
     */
    public String getRawMessage() {
        ExceptionChain rawExceptionInfo = this.getRawExceptionInfo();
        return rawExceptionInfo == null ? null : rawExceptionInfo.getMessage();
    }

    public ExceptionChain getRawExceptionInfo() {
        return CollectionUtils.isEmpty(exceptionChains) ? null : exceptionChains.get(0);
    }


    @Override
    public void printStackTrace() {
        if (CollectionUtils.isEmpty(exceptionChains)) {
            super.printStackTrace();
        } else {
            PrintStream err = System.err;
            ExceptionChain exceptionChain = exceptionChains.get(0);
            String exceptionClass = exceptionChain.getExceptionClass();
            String title = String.format("The exception may not have occurred in the current service. exception class:'%s',and message is:'%s'", exceptionClass, exceptionChain.getMessage());
            err.println(title);
            String strings = "yyyy-MM-dd HH:mm:ss.SSS";
            int size = exceptionChains.size();
            for (int i = 0; i < size; i++) {
                ExceptionChain chain = exceptionChains.get(i);
                String format = DateFormatUtils.format(chain.getTimestamp(), strings);
                String status = i == 0 ? "HAPPEN" : "THROW";
                boolean isFinal = size - 1 == i;
                String message = String.format("[%s] timestamp:'%s',exceptionClass:'%s',message:'%s',path:'%s'", status, format, chain.getExceptionClass(), chain.getMessage(), chain.getPath());
                err.println(message);
                if (!isFinal) {
                    err.println("↓↓↓↓Throw an exception↓↓↓↓");
                }
            }
        }
    }
}
