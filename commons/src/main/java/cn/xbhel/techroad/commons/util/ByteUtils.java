package cn.xbhel.techroad.commons.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字节操作工具类，可以参考 HBase Bytes 类
 *
 * @author xbhel
 */
public final class ByteUtils {

    private ByteUtils() {
    }

    public static byte[] toBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(String str, Charset charset) {
        return str.getBytes(charset);
    }

    public static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String toString(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }

}
