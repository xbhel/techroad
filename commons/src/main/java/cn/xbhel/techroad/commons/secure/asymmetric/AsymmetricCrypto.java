package cn.xbhel.techroad.commons.secure.asymmetric;

/**
 * 非对称加密
 *
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
