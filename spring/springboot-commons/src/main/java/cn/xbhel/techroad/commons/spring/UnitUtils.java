package cn.xbhel.techroad.commons.spring;

import org.springframework.util.unit.DataSize;

import java.util.function.Consumer;
import java.util.function.LongConsumer;

/**
 * 常用的类型转换工具类
 * @author xbhel
 */
public final class UnitUtils {

    private UnitUtils() {}

    public static Consumer<DataSize> in(LongConsumer consumer) {
        return dataSize -> consumer.accept(getBytes(dataSize));
    }

    public static long getBytes(DataSize dataSize) {
        long bytes = dataSize.toBytes();
        return bytes > 0 ? bytes : 0;
    }

}
