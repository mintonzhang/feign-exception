package cn.minsin.feign.annotation;

import cn.minsin.feign.config.RegistryExceptionDecoder;
import cn.minsin.feign.config.RegistryExceptionHandler;
import cn.minsin.feign.default_.FeignExceptionDecoder;
import cn.minsin.feign.default_.FeignExceptionHandler;
import feign.codec.ErrorDecoder;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Import;

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
@Import({RegistryExceptionHandler.class, RegistryExceptionDecoder.class})
public @interface EnableFeignExceptionHandler {

    /**
     * feign异常处理
     */
    Class<? extends ErrorAttributes> handlerClass() default FeignExceptionHandler.class;

    /**
     * feign 异常处理器类
     */
    Class<? extends ErrorDecoder> decoderClass() default FeignExceptionDecoder.class;
}
