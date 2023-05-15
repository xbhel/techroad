package cn.xbhel.techroad.commons.secure;

import cn.xbhel.techroad.commons.util.FileUtils;

import java.math.BigInteger;
import java.nio.file.Path;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;

/**
 * 用于生成密钥
 *
 * @author xbhel
 */
public final class KeyUtils {

    private static final String RSA_ALGORITHM = "RSA";

    private KeyUtils() {
    }

    /**
     * 创建密钥对
     */
    public static KeyPair getKeyPair(int keySize, String algorithm) {
        return getKeyPair(keySize, null, algorithm);
    }

    /**
     * 使用指定的 seed 生成随机数，然后创建密钥对.
     * 注意：seed 应该是不可推测的，seed 的随机性影响着算法的安全性，
     * 一种可行的方案是使用 {@code SecureRandom.generateSeed} 去生成种子。
     */
    public static KeyPair getKeyPair(int keySize, byte[] seed, String algorithm) {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            var random = new SecureRandom();
            if (seed != null) random.setSeed(seed);
            keyPairGenerator.initialize(keySize, random);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 从指定路径获取公钥
     */
    public static PublicKey getPublicKey(String algorithm, Path path) {
        return getPublicKey(algorithm, FileUtils.readAllBytes(path));
    }

    /**
     * 从指定路径获取私钥
     */
    public static PrivateKey getPrivateKey(String algorithm, Path path) {
        return getPrivateKey(algorithm, FileUtils.readAllBytes(path));
    }

    /**
     * 获取公钥. X509EncodedKeySpec 是 ASN.1 编码的公钥规范.
     * X509EncodedKeySpec represents the ASN.1 encoding of a public key,
     * encoded according to the ASN.1 type {@code SubjectPublicKeyInfo}.
     */
    public static PublicKey getPublicKey(String algorithm, byte[] publicKey) {
        var keySpec = new X509EncodedKeySpec(publicKey);
        return getPublicKey(algorithm, keySpec);
    }

    /**
     * 获取私钥，PKCS8EncodedKeySpec 是 ASN.1 编码的私钥规范.
     * PKCS8EncodedKeySpec represents the ASN.1 encoding of a private key,
     * encoded according to the ASN.1 type {@code PrivateKeyInfo}.
     */
    public static PrivateKey getPrivateKey(String algorithm, byte[] privateKey) {
        var keySpec = new PKCS8EncodedKeySpec(privateKey);
        return getPrivateKey(algorithm, keySpec);
    }

    public static PrivateKey getPrivateKey(final String algorithm, KeySpec keySpec) {
        try {
            var keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    public static PublicKey getPublicKey(final String algorithm, KeySpec keySpec) {
        try {
            var keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    public static RSAPublicKey getPublicKey(byte[] modulus, byte[] exponent) {
        var keySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(exponent));
        return (RSAPublicKey) KeyUtils.getPublicKey(RSA_ALGORITHM, keySpec);
    }

    public static RSAPrivateKey getPrivateKey(byte[] modulus, byte[] exponent) {
        var keySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(exponent));
        return (RSAPrivateKey) KeyUtils.getPrivateKey(RSA_ALGORITHM, keySpec);
    }
}
