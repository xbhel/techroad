package cn.xbhel.techroad.commons.secure.asymmetric;

import cn.xbhel.techroad.commons.util.Base64Utils;
import cn.xbhel.techroad.commons.util.HexUtils;

/**
 * 非对称加密
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 *
 * @author xbhel
 */
public interface AsymmetricCrypto {

    /**
     * 解密
     *
     * @param data    要解密的数据
     * @param keyType 密钥类型
     */
    byte[] decrypt(byte[] data, KeyType keyType);

    /**
     * 加密
     *
     * @param data    要加密的数据
     * @param keyType 密钥类型
     */
    byte[] encrypt(byte[] data, KeyType keyType);

    /**
     * 以 16 进制返回加密数据
     */
    default String encryptToHex(byte[] data, KeyType keyType) {
        return HexUtils.encodeString(encrypt(data, keyType));
    }

    /**
     * 以 Base64 编码返回加密数据
     */
    default String encryptToBase64(byte[] data, KeyType keyType) {
        return Base64Utils.encodeString(encrypt(data, keyType));
    }

    /**
     * 解密 16 进制数据
     */
    default byte[] decryptFromHex(String hexStr, KeyType keyType) {
        return decrypt(HexUtils.decode(hexStr), keyType);
    }

    /**
     * 解密 Base64 编码数据
     */
    default byte[] decryptFromBase64(String base64Str, KeyType keyType) {
        return decrypt(Base64Utils.decode(base64Str), keyType);
    }

    enum KeyType {
        PRIVATE_KEY,
        PUBLIC_KEY;
    }
}
