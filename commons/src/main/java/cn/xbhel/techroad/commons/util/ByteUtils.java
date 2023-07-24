package cn.xbhel.techroad.commons.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字节操作工具类
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

    public static byte[] toBytes(int n) {
        // LITTLE_ENDIAN 小端字节顺序
        byte[] bytes = new byte[Integer.BYTES];
        bytes[3] = (byte) ((n >>> 24) & 0xff);
        bytes[2] = (byte) ((n >>> 16) & 0xff);
        bytes[1] = (byte) ((n >>> 8) & 0xff);
        bytes[0] = (byte) (n & 0xff);
        return bytes;
    }

    public static int toInt(byte[] bytes) {
        // 使用 LITTLE_ENDIAN 小端字节还原
        return (bytes[0] & 0xff) | (bytes[1] << 8) | (bytes[2] << 16) | (bytes[3] << 24);
    }

}
