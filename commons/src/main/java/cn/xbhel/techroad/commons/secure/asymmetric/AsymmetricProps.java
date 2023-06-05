package cn.xbhel.techroad.commons.secure.asymmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 非对称加密属性，用于通过不同参数创建 {@link AbstractAsymmetricCrypto} 实现。
 * 如果将不同创建方式{@link #of}以构造形式集成至 {@link AbstractAsymmetricCrypto} 中，那么每个子类都需要重载所有构造函数
 * 才可以覆盖所有创建方式，将这段逻辑提取出来以避免这种情况。
 */
public class AsymmetricProps {
    /** 算法 */
    protected String algorithm;
    /** 公钥 */
    protected PublicKey publicKey;
    /** 私钥 */
    protected PrivateKey privateKey;

    protected AsymmetricProps(String algorithm, PublicKey publicKey, PrivateKey privateKey) {
        this.algorithm = algorithm;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public static AsymmetricProps of(AsymmetricAlgorithm algorithm) {
        return of(algorithm.getName(), algorithm.getKeySize());
    }

    public static AsymmetricProps of(String algorithm, int keySize) {
        var keyPair = KeyUtils.getKeyPair(algorithm, keySize);
        return of(algorithm, keyPair.getPublic(), keyPair.getPrivate());
    }

    public static AsymmetricProps of(String algorithm, PublicKey publicKey, PrivateKey privateKey) {
        return new AsymmetricProps(algorithm, publicKey, privateKey);
    }
}
