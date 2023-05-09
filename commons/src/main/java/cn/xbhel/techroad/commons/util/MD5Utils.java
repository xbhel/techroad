package cn.xbhel.techroad.commons.util;

import cn.xbhel.techroad.commons.secure.MessageDigester;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * MD5 消息摘要算法是一种密码破译但仍被广泛使用的散列函数，可生成 128 位(16个字符)散列值，
 * MD5算法无法防止碰撞攻击，因此不适用于安全性认证，如 SSL公开密钥认证或是数字签名等用途，
 * 但可以用作校验和来验证数据完整性，对于需要高度安全性的资料建议改用其他算法，如 SHA-2。
 * 一般 128 位的 MD5 散列被表示为 32 位十六进制数字。
 *
 * @author xbhel
 */
public final class MD5Utils {

    private static final ThreadLocal<MessageDigester> DIGESTER_FACTORY =
            ThreadLocal.withInitial(() -> MessageDigester.of("MD5"));

    private MD5Utils() {
    }

    public static String digestToHex(byte[] data) {
        return HexUtils.encodeHexString(DIGESTER_FACTORY.get().digest(data));
    }

    public static String digestToHex(InputStream in) throws IOException {
        return HexUtils.encodeHexString(DIGESTER_FACTORY.get().digest(in));
    }

    public static String digestToHex(File file) throws IOException {
        return HexUtils.encodeHexString(DIGESTER_FACTORY.get().digest(file));
    }

    public static void removeDigesterFromFactory() {
        DIGESTER_FACTORY.remove();
    }

}
