package cn.xbhel.techroad.commons.secure;

import cn.xbhel.techroad.commons.util.FileUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 用于生成密钥
 *
 * @author xbhel
 */
public final class KeyUtils {

    private KeyUtils() {
    }

    public static SecretKey getKey(String algorithm) {
        return getKey(-1, null, algorithm);
    }

    public static SecretKey getKey(int keySize, String algorithm) {
        return getKey(keySize, null, algorithm);
    }

    /**
     * 使用指定的 seed 生成随机数，然后创建密钥对.
     * 注意：seed 应该是不可推测的，seed 的随机性影响着算法的安全性，
     * 一种可行的方案是使用 {@code SecureRandom.generateSeed} 去生成种子.
     */
    public static SecretKey getKey(int keySize, byte[] seed, String algorithm) {
        try {
            var keyGenerator = KeyGenerator.getInstance(algorithm);
            var random = new SecureRandom();
            // 设置种子
            if (seed != null) random.setSeed(seed);
            // keySize 没有指定则使用算法默认的
            if (keySize > 0) {
                keyGenerator.init(keySize, random);
            } else {
                keyGenerator.init(random);
            }
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 创建密钥对
     */
    public static KeyPair getKeyPair(String algorithm, int keySize) {
        return getKeyPair(algorithm, keySize, null);
    }

    /**
     * 使用指定的 seed 生成随机数，然后创建密钥对.
     */
    public static KeyPair getKeyPair(String algorithm, int keySize, byte[] seed) {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance(algorithm,
                    GlobalProvider.INSTANCE.getProvider());
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

    public static PrivateKey getPrivateKey(String algorithm, KeySpec keySpec) {
        try {
            var keyFactory = KeyFactory.getInstance(algorithm,
                    GlobalProvider.INSTANCE.getProvider());
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    public static PublicKey getPublicKey(String algorithm, KeySpec keySpec) {
        try {
            var keyFactory = KeyFactory.getInstance(algorithm,
                    GlobalProvider.INSTANCE.getProvider());
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}
