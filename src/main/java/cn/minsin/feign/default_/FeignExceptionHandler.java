package cn.minsin.feign.default_;

import cn.minsin.feign.config.FeignExceptionHandlerContext;
import cn.minsin.feign.constant.ExceptionConstant;
import cn.minsin.feign.exception.RemoteCallException;
import cn.minsin.feign.model.ExceptionChain;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 当服务内报错 返回给Feign的处理器
 */
@Slf4j
public class FeignExceptionHandler extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Throwable error = super.getError(webRequest);
        List<ExceptionChain> exceptionChains = null;
        if (error instanceof RemoteCallException) {
            exceptionChains = ((RemoteCallException) error).getExceptionChains();
        } else {
            Object attribute = webRequest.getAttribute(ExceptionConstant.EXCEPTION_CHAIN_KEY, RequestAttributes.SCOPE_REQUEST);
            if (attribute != null) {
                exceptionChains = JSON.parseArray(attribute.toString(), ExceptionChain.class);
            }
            if (exceptionChains == null) {
                exceptionChains = new ArrayList<>(1);
            }
        }

        ExceptionChain exceptionChain = new ExceptionChain();
        exceptionChain.setMessage(error.getMessage());
        exceptionChain.setPath(errorAttributes.get("path").toString());
        exceptionChain.setTimestamp(new Date());
        exceptionChain.setApplicationName(FeignExceptionHandlerContext.getApplicationName());
        //添加发生的异常类信息 以便下一步处理
        if (error.getClass() != null) {
            exceptionChain.setExceptionClass(error.getClass().getTypeName());
        }
        exceptionChains.add(exceptionChain);
        errorAttributes.put(ExceptionConstant.EXCEPTION_CHAIN_KEY, exceptionChains);
        return errorAttributes;
    }
}

