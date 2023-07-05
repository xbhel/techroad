package cn.xbhel.techroad.commons.secure.digest;

/**
 * SHA(安全的 Hash 算法)是一种流行的加密散列函数，加密散列可用作文本或数据文件签名，目前，SHA-2 散列被广泛的使用，
 * 被认为是密码学领域是最安全的散列算法！
 * SHA-256 算法产生一个几乎唯一、定长265位(32字节)的 hash 值，SHA-256 是一种单向散列函数，所以结果无法解密回原始值.
 * <p/>
 * NIST 在2015年发布了 SHA-3，SHA-3 是 SHA-2 之后最新的安全散列标准，与 SHA-2 相比，
 * SHA-3 提供了一种不同的方法来生成唯一的单向散列，并且在某些硬件实现上更快！暂时没有 SHA-2 那么多的 SHA-3 库，
 * 直到 JDK 9 才在内置默认提供程序中提供 SHA-3 算法。
 *
 * @author xbhel
 */
public final class ThreadLocalSHA {
    private static final String ALGORITHM = "SHA-256";
    private static final ThreadLocal<DigestUtils> DIGESTER_FACTORY =
            ThreadLocal.withInitial(() -> DigestUtils.of(ALGORITHM));

    private ThreadLocalSHA() {
    }

    public static DigestUtils get() {
        return DIGESTER_FACTORY.get();
    }

    public static void remove() {
        DIGESTER_FACTORY.remove();
    }
}
