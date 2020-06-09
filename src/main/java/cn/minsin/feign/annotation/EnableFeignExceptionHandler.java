package cn.minsin.feign.annotation;

import cn.minsin.feign.config.FeignConfiguration;
import cn.minsin.feign.config.OverriderErrorMvcAutoConfiguration;
import cn.minsin.feign.config.RequestMappingHandlerMappingConfiguration;
import cn.minsin.feign.util.FeignExceptionHandlerContext;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 21:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({EnableFeignExceptionHandler.EnvironmentAwareGet.class})
//导入springMVC判断controller接口的配置
@ImportAutoConfiguration(classes = {RequestMappingHandlerMappingConfiguration.class, FeignConfiguration.class, OverriderErrorMvcAutoConfiguration.class})
public @interface EnableFeignExceptionHandler {

    class EnvironmentAwareGet implements EnvironmentAware {

        @Override
        public void setEnvironment(Environment environment) {
            FeignExceptionHandlerContext.setEnvironment(environment);
        }
    }
}
