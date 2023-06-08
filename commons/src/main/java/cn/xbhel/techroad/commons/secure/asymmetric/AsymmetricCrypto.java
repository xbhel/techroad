package cn.xbhel.techroad.commons.secure.asymmetric;

/**
 * 非对称加密
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * @author xbhel
 */
public interface AsymmetricCrypto {

    /**
     * 加密
     * @param data 要加密的数据
     * @param keyType 密钥类型
     */
    byte[] decrypt(byte[] data, KeyType keyType);

    /**
     * 解密
     * @param data 要解密的数据
     * @param keyType 密钥类型
     */
    byte[] encrypt(byte[] data, KeyType keyType);

    enum KeyType {
        PRIVATE_KEY,
        PUBLIC_KEY;
    }
}
