package cn.xbhel.techroad.commons.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 编码和解码。
 * MD5 消息摘要算法是一种密码破译但仍被广泛使用的散列函数，可生成 128 位（16个字符(BYTES)）散列值，
 * 尽管 MD5 最初设计为用作加密散列函数，但已发现它存在大量漏洞。它仍然可以用作校验和来验证数据完整性，
 * 但只能用于防止意外损坏，也适用于其他非加密目的，例如确定分区数据库中特定密钥的分区。
 * <p>
 * MD5算法无法防止碰撞攻击，因此不适用于安全性认证，如 SSL公开密钥认证或是数字签名等用途。
 * 对于需要高度安全性的资料，专家一般建议改用其他算法，如 SHA-2。一般 128 位的 MD5 散列被表示为 32 位十六进制数字。
 *
 * @author xbhel
 */
public final class MD5Utils {

    private static final String ALGORITHM = "MD5";
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    /**
     * {@code MessageDigest}为应用程序提供了一个消息摘要算法，例如 MD5、SHA-1、SHA-256(默认支持这三种)，
     * 消息摘要是一个 one-way(单项) 的散列函数，它采用任意大小数据并输出一个固定长度的哈希值。
     */
    private static final ThreadLocal<MessageDigest> DIGESTER_FACTORY =
            ThreadLocal.withInitial(() -> {
                try {
                    return MessageDigest.getInstance(ALGORITHM);
                } catch (NoSuchAlgorithmException e) {
                    throw new IllegalArgumentException(e);
                }
            });

    private MD5Utils() {
    }

    public static byte[] encode(byte[] data) {
        var digest = getDigester();
        digest.update(data);
        return digest.digest();
    }

    public static byte[] encode(InputStream in) {
        var digest = getDigester();
        var bytes = new byte[DEFAULT_BUFFER_SIZE];
        try (var bi = new BufferedInputStream(in)) {
            while (bi.read(bytes) != -1) {
                digest.update(bytes);
            }
            return digest.digest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void removeDigester() {
        DIGESTER_FACTORY.remove();
    }

    public static MessageDigest getDigester() {
        var digest = DIGESTER_FACTORY.get();
        digest.reset();
        return digest;
    }
}
