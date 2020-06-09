package cn.minsin.feign.util;

import org.springframework.core.env.Environment;

/**
 * @author: minton.zhang
 * @since: 2020/6/4 14:54
 */
public final class FeignExceptionHandlerContext {


    private static Environment ENVIRONMENT;


    public static String getApplicationName() {
        return ENVIRONMENT == null ? "unknownServer" : ENVIRONMENT.getProperty("spring.application.name");
    }

    public static String getProperty(String name) {
        return ENVIRONMENT == null ? "unknownServer" : ENVIRONMENT.getProperty(name);
    }

    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    public static void setEnvironment(Environment environment) {
        if (ENVIRONMENT == null) {
            FeignExceptionHandlerContext.ENVIRONMENT = environment;
        }
    }

}
