package cn.xbhel.techroad.commons.secure.digest;

/**
 * MD5 消息摘要算法是一种密码破译但仍被广泛使用的散列函数，可生成 128 位(16个字符)散列值，
 * MD5算法无法防止碰撞攻击，因此不适用于安全性认证，如 SSL公开密钥认证或是数字签名等用途，
 * 但可以用作校验和来验证数据完整性，对于需要高度安全性的资料建议改用其他算法，如 SHA-2。
 * 一般 128 位的 MD5 散列被表示为 32 位十六进制数字。
 *
 * @author xbhel
 */
public final class ThreadLocalMD5 {

    private static final String ALGORITHM = "MD5";
    private static final ThreadLocal<DigestUtils> DIGESTER_FACTORY =
            ThreadLocal.withInitial(() -> DigestUtils.of(ALGORITHM));

    private ThreadLocalMD5() {
    }

    public static DigestUtils get() {
        return DIGESTER_FACTORY.get();
    }

    public static void remove() {
        DIGESTER_FACTORY.remove();
    }
}
