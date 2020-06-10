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

    /**
     * 获取配置项
     *
     * @param name 配置名称
     * @return
     */
    public static String getProperty(String name) {
        return ENVIRONMENT == null ? null : ENVIRONMENT.getProperty(name);
    }

    /**
     * 获取配置项
     *
     * @param name         配置名称
     * @param defaultValue 默认值
     */
    public static String getProperty(String name, String defaultValue) {
        return ENVIRONMENT == null ? defaultValue : ENVIRONMENT.getProperty(name, defaultValue);
    }

    /**
     * 获取环境对象
     */
    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    public static void setEnvironment(Environment environment) {
        if (ENVIRONMENT == null) {
            FeignExceptionHandlerContext.ENVIRONMENT = environment;
        }
    }

}
