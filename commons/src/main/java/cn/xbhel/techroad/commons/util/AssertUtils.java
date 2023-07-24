package cn.xbhel.techroad.commons.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * 断言工具类
 * @author xbhel
 */
public final class AssertUtils {

    private AssertUtils() {}

    public static void notNull(String errMessage, Object...params) {
        boolean state = Arrays.stream(params).allMatch(Objects::nonNull);
        condition(state, errMessage);
    }

    public static void condition(boolean state, String errMessage) {
        if(!state) {
            throw new IllegalArgumentException(errMessage);
        }
    }
}
