package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.util.Base64Utils;
import cn.xbhel.techroad.commons.util.HexUtils;

/**
 * 对称加密接口
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
