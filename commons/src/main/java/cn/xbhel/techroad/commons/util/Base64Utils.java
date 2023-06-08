package cn.xbhel.techroad.commons.util;

import java.util.Base64;

/**
 * Base64 编码工具类
 *
 * @author xbhel
 */
public final class Base64Utils {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private Base64Utils() {
    }

    public static byte[] encode(byte[] data) {
        return ENCODER.encode(data);
    }

    public static String encodeString(byte[] data) {
        return ENCODER.encodeToString(data);
    }

    public static byte[] decode(byte[] data) {
        return DECODER.decode(data);
    }

    public static byte[] decode(String data) {
        return DECODER.decode(data);
    }
}
