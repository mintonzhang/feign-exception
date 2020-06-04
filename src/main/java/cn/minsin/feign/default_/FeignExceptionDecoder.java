package cn.minsin.feign.default_;

import cn.minsin.feign.exception.RemoteCallException;
import cn.minsin.feign.model.ExceptionModel;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;

/**
 * 当调用远程服务 其抛出异常捕获
 *
 * @author: minton.zhang
 * @since: 2020/6/3 17:58
 */
@Slf4j
public class FeignExceptionDecoder implements ErrorDecoder {
    public Exception decode(String methodKey, Response response) {
        try {
            Reader reader = response.body().asReader();
            String body = Util.toString(reader);
            ExceptionModel exceptionModel = JSONObject.parseObject(body, ExceptionModel.class);
            return new RemoteCallException(exceptionModel.getMessage());
        } catch (Exception e) {
            log.error("{} has an unknown exception.", methodKey, e);
            return new RemoteCallException("unKnowException", e);
        }

    }
}
