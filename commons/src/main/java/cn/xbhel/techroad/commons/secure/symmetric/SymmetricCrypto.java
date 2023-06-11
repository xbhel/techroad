package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.util.Base64Utils;
import cn.xbhel.techroad.commons.util.HexUtils;

/**
 * 对称加密接口
 * 对称加密算法指的是使用相同密钥进行加密和解密的算法，以下是常见的对称加密算法：
 * 1. DES（Data Encryption Standard）
 * DES 是一种广泛使用的对称加密算法，使用 56 位密钥对数据进行加密和解密。但由于其密钥长度较短，
 * 容易受到暴力破解攻击，因此在实际应用中逐渐被更安全的算法所取代。
 *
 * 2. 3DES（Triple Data Encryption Standard）
 * 3DES 是对 DES 的改进版本，通过应用 DES 算法三次，使用两个或三个不同的密钥，提供更高的安全性。
 * 它在现代加密标准中仍然得到广泛应用，但由于其较慢的处理速度，已逐渐被更高效的算法取代。
 *
 * 3. AES（Advanced Encryption Standard）
 * AES 是目前最常用的对称加密算法之一。它支持不同的密钥长度（128、192 或 256 位），
 * 并被广泛用于保护敏感数据的安全性。AES 算法具有较高的安全性和性能，在许多应用中被广泛采用，
 * 包括网络通信、数据存储等领域。
 *
 * 4. RC4（Rivest Cipher 4）
 * RC4 是一种流密码（Stream Cipher）算法，广泛应用于数据加密通信和协议中。
 * 它以快速和简单著称，但在一些特定情况下可能存在安全性问题，因此在许多应用中被更安全的算法所取代。
 *
 * 5. Blowfish
 * Blowfish 是一种对称分组密码算法，它使用可变长度的密钥（32 到 448 位）和分块加密技术。
 * Blowfish 算法的优点在于它的快速性和可扩展性，适用于各种不同的平台和应用。
 *
 * @author xbhel
 */
public interface SymmetricCrypto {

    /**
     * 解密
     * @param data 要解密的数据
     */
    byte[] decrypt(byte[] data);

    /**
     * 加密
     * @param data 要加密的数据
     */
    byte[] encrypt(byte[] data);

    /**
     * 以 16 进制返回加密数据
     */
    default String encryptToHex(byte[] data) {
        return HexUtils.encodeString(encrypt(data));
    }

    /**
     * 以 Base64 编码返回加密数据
     */
    default String encryptToBase64(byte[] data) {
        return Base64Utils.encodeString(encrypt(data));
    }

    /**
     * 解密 16 进制数据
     */
    default byte[] decryptFromHex(String hexStr) {
        return decrypt(HexUtils.decode(hexStr));
    }

    /**
     * 解密 Base64 编码数据
     */
    default byte[] decryptFromBase64(String base64Str) {
        return decrypt(Base64Utils.decode(base64Str));
    }
}
