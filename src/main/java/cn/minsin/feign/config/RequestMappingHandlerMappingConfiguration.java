package cn.minsin.feign.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author: minton.zhang
 * @since: 2020/6/9 16:07
 */
@Configuration
public class RequestMappingHandlerMappingConfiguration implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new FeignRequestMappingHandlerMapping();
    }

    private static class FeignRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        protected boolean isHandler(Class<?> beanType) {
            //1 父类处理器 2 不能包含feignClient 3 不能是接口
            return super.isHandler(beanType)
                    && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class)
                    && !beanType.isInterface();
        }
    }
}
