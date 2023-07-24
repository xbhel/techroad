package cn.xbhel.techroad.commons.util;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 字符串工具类
 * @author xbhel
 */
public final class StringUtils {
    private StringUtils() {}

    public static boolean anyEndWith(String str, Collection<String> suffixes) {
        if(str == null) return false;
        return match(Stream::anyMatch, suffixes, str::endsWith);
    }

    private static boolean match(BiPredicate<Stream<String>, Predicate<String>> matcher,
                                 Collection<String> coll,
                                 Predicate<String> predicate) {
         if(CollUtil.isEmpty(coll)) return false;
         return matcher.test(coll.stream(), predicate);
    }
}
