package cn.xbhel.techroad.commons.secure.asymmetric;

import cn.xbhel.techroad.commons.util.Base64Utils;
import cn.xbhel.techroad.commons.util.HexUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * 非对称加密算法
 * 1. 加密：使用公钥加密，私钥解密；保证只有私钥的拥有者才能进行解密，其他人无法获得信息，但是无法保证信息不被篡改.
 * 2. 签名：使用私钥加密，公钥解密；可以防止私钥所有者发布的信息被伪造和篡改，但是无法保证信息不被获得.
 *
 * @author xbhel
 */
public abstract class AbstractAsymmetricOperator {

    /**
     * 常量用于决定要使用的密钥：私钥
     */
    public static final int PRIVATE_KEY_TYPE = 0x01;
    /**
     * 常量用于决定要使用的密钥：公钥
     */
    public static final int PUBLIC_KEY_TYPE = 0x02;

    protected Cipher cipher;
    protected PrivateKey privateKey;
    protected PublicKey publicKey;

    /**
     * 创建时可以私钥和公钥可以仅传递其中一个，此时只能用来解密或加密，也可以在后续调用 set 方法进行赋值.
     *
     * @param algorithm  算法
     * @param publicKey  私钥
     * @param privateKey 公钥
     */
    protected AbstractAsymmetricOperator(String algorithm, PrivateKey privateKey, PublicKey publicKey) {
        this.cipher = initCipher(algorithm);
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public abstract byte[] encrypt(byte[] data, int keyType);

    public abstract byte[] decrypt(byte[] data, int keyType);

    public String encryptToHex(byte[] data, int keyType) {
        return HexUtils.encodeString(encrypt(data, keyType));
    }

    public String encryptToBase64(byte[] data, int keyType) {
        return Base64Utils.encodeString(encrypt(data, keyType));
    }

    public byte[] decryptFromHex(String hexString, int keyType) {
        return decrypt(HexUtils.decode(hexString), keyType);
    }

    public byte[] decryptFromBase64(String base64String, int keyType) {
        return decrypt(Base64Utils.decode(base64String), keyType);
    }

    public Key getKey(int keyType) {
        if (Objects.equals(PRIVATE_KEY_TYPE, keyType)) {
            return Objects.requireNonNull(this.privateKey,
                    "Private key must be not null when use it.");
        }
        if (Objects.equals(PUBLIC_KEY_TYPE, keyType)) {
            return Objects.requireNonNull(this.publicKey,
                    "Public key must be not null when use it.");
        }
        throw new IllegalArgumentException("Unsupported key type: " + keyType);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    private static Cipher initCipher(String algorithm) {
        try {
            return Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }
}
