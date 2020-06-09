package cn.minsin.feign.config;

import cn.minsin.feign.exception.BaseRemoteCallException;
import cn.minsin.feign.exception.RemoteCallException;
import cn.minsin.feign.model.ExceptionModel;
import com.alibaba.fastjson.JSON;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;

/**
 * 当调用远程服务 其抛出异常捕获
 *
 * @author: minton.zhang
 * @since: 2020/6/3 17:58
 */
@Slf4j
@RequiredArgsConstructor
public class FeignExceptionDecoder implements ErrorDecoder {

    private final BaseRemoteCallException baseRemoteCallException;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            //↓↓↓↓↓↓↓此处千万别打断点，不然会报 stream is closed的异常↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
            Reader reader = response.body().asReader();
            String body = Util.toString(reader);
            //↑↑↑↑↑↑↑此处千万别打断点，不然会报 stream is closed的异常↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
            ExceptionModel exceptionModel = JSON.parseObject(body, ExceptionModel.class);
            return baseRemoteCallException.throwException(exceptionModel.getMessage(), exceptionModel.getExceptionChain());
        } catch (Exception e) {
            log.error("{} has an unknown exception.", methodKey, e);
            return new RemoteCallException("unKnowException", e);
        }

    }
}
