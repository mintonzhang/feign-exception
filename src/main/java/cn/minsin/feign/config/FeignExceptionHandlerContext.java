package cn.minsin.feign.config;

/**
 * @author: minton.zhang
 * @since: 2020/6/4 14:54
 */
public final class FeignExceptionHandlerContext {


    private static String APPLICATION_NAME;


    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    public static void setApplicationName(String applicationName) {
        if (APPLICATION_NAME == null) {
            APPLICATION_NAME = applicationName;
        }
    }
}
