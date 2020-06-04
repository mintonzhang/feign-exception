package cn.minsin.feign.default_;

import cn.minsin.feign.constant.ExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.WebRequest;

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
        String qualifiedName = ClassUtils.getQualifiedName(error.getClass());
        //添加发生的异常类信息 以便下一步处理
        errorAttributes.put(ExceptionConstant.THROW_EXCEPTION_CLASS, qualifiedName);
        return errorAttributes;
    }


}

