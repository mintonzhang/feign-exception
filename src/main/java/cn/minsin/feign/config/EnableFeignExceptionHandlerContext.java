package cn.minsin.feign.config;

import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author: minton.zhang
 * @since: 2020/6/4 14:54
 */
public class EnableFeignExceptionHandlerContext {

    private static AnnotationAttributes ENABLE_FEIGN_EXCEPTION_HANDLER;

    public static void setEnableFeignExceptionHandler(AnnotationAttributes enableFeignExceptionHandler) {
        if (ENABLE_FEIGN_EXCEPTION_HANDLER == null) {
            ENABLE_FEIGN_EXCEPTION_HANDLER = enableFeignExceptionHandler;
        }
    }

}
