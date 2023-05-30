package cn.xbhel.techroad.commons.secure.asymmetric;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * 非对称加密通用逻辑
 * @author xbhel
 */
public abstract class AbstractAsymmetricCrypto implements AsymmetricCrypto{

    protected Cipher cipher;
    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    /**
     * 创建时可以私钥和公钥可以仅传递其中一个，此时只能用来解密或加密，也可以在后续调用 set 方法进行赋值.
     *
     * @param algorithm  算法
     * @param publicKey  私钥
     * @param privateKey 公钥
     */
    protected AbstractAsymmetricCrypto(String algorithm, PublicKey publicKey, PrivateKey privateKey) {
        this.cipher = initCipher(algorithm);
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public Key getKey(KeyType keyType) {
        if (keyType == KeyType.PRIVATE_KEY) {
            return Objects.requireNonNull(this.privateKey,
                    "Private key must be not null when use it.");
        }
        if (keyType == KeyType.PUBLIC_KEY) {
            return Objects.requireNonNull(this.publicKey,
                    "Public key must be not null when use it.");
        }
        throw new IllegalArgumentException("Unsupported key type: " + keyType);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    private static Cipher initCipher(String algorithm) {
        try {
            return Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }
}
