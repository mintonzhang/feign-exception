package cn.minsin.feign.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author: minton.zhang
 * @since: 2020/6/5 17:57
 */
public class StringUtil {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <Input> boolean isBlank(Input verifiedData) {
        if (verifiedData != null && verifiedData.getClass().isArray()) {
            return ((Input[]) verifiedData).length == 0;
        } else if (verifiedData instanceof Collection) {
            return ((Collection) verifiedData).isEmpty();
        } else if (verifiedData instanceof Map) {
            return ((Map) verifiedData).isEmpty();
        } else if (verifiedData instanceof CharSequence) {
            return ((CharSequence) verifiedData).length() == 0;
        } else {
            return verifiedData == null;
        }
    }
}
