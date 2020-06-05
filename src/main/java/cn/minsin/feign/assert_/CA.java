package cn.minsin.feign.assert_;

import cn.minsin.feign.util.StringUtil;
import lombok.NonNull;

import java.util.Objects;

/**
 * C mean Condition
 * A mean Assert
 *
 * @author: minton.zhang
 * @since: 2020/5/26 17:02
 */
public interface CA {


    /**
     * 设置全局默认异常 只需要设置一次，后面设置的会被把前面设置的覆盖掉
     * 默认 {@linkplain RuntimeException}
     */

    static void setDefaultException(@NonNull Class<? extends RuntimeException> defaultException) {
        Then.setDefaultException(defaultException);
    }


    /**
     * 是否不为空 一个不为空则返回true
     */
    static Then isNotNull(Object... param) {
        boolean flag = false;
        for (Object o : param) {
            if (o != null) {
                flag = true;
                break;
            }
        }
        return new Then(flag);
    }

    /**
     * 是否不为空 一个为空则返回true
     */
    static Then isNull(Object... param) {
        boolean flag = false;
        for (Object o : param) {
            if (o == null) {
                flag = true;
                break;
            }
        }
        return new Then(flag);
    }

    /**
     * 如果不相等则返回true
     */
    static Then isNotEqual(Object arg1, Object arg2) {
        return new Then(Objects.equals(arg1, arg2));
    }

    /**
     * 如果相等则返回true
     */
    static Then isEqual(Object arg1, Object arg2) {
        return new Then(!Objects.equals(arg1, arg2));
    }

    /**
     * 如果为true则返回true
     */
    static Then isTrue(boolean expression) {
        return new Then(expression);
    }

    /**
     * 如果为false则返回true
     */
    static Then isFalse(boolean expression) {
        return new Then(expression);
    }

    /**
     * 判断对象是否为空
     */
    static <T> Then isEmpty(T param) {
        return new Then(StringUtil.isBlank(param));
    }
}