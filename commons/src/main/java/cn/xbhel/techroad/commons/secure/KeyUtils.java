package cn.xbhel.techroad.commons.secure;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
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

    public static SecretKey getKey(String algorithm, byte[] key) {
        var keySpec = new SecretKeySpec(key, algorithm);
        try {
            var factory = SecretKeyFactory.getInstance(algorithm, GlobalProvider.INSTANCE.getProvider());
            return factory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CryptoException(e);
        }
    }

    public static SecretKey getKey(String algorithm, int keySize) {
        return getKey(algorithm, keySize, (byte[]) null);
    }

    /**
     * 使用指定的 seed 生成随机数，然后创建密钥对.
     * 注意：seed 应该是不可推测的，seed 的随机性影响着算法的安全性，
     * 一种可行的方案是使用 {@code SecureRandom.generateSeed} 去生成种子.
     */
    public static SecretKey getKey(String algorithm, int keySize, byte[] seed) {
        var random = new SecureRandom();
        if (seed != null) random.setSeed(seed);
        return getKey(algorithm, keySize, random);
    }

    public static SecretKey getKey(String algorithm, int keySize, SecureRandom random) {
        try {
            var keyGenerator = KeyGenerator.getInstance(
                    getMainAlgorithm(algorithm), GlobalProvider.INSTANCE.getProvider());
            // keySize 没有指定则使用算法默认的
            if (keySize > 0) {
                keyGenerator.init(keySize, random);
            } else {
                keyGenerator.init(random);
            }
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
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
            var keyPairGenerator = KeyPairGenerator.getInstance(
                    getMainAlgorithm(algorithm), GlobalProvider.INSTANCE.getProvider());
            var random = new SecureRandom();
            if (seed != null) {
                random.setSeed(seed);
            }
            keyPairGenerator.initialize(keySize, random);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 从流中获取公钥
     */
    public static PublicKey getPublicKey(String algorithm, InputStream in) {
        try {
            return getPublicKey(algorithm, in.readAllBytes());
        } catch (IOException e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 从流中获取私钥
     */
    public static PrivateKey getPrivateKey(String algorithm, InputStream in) {
        try {
            return getPrivateKey(algorithm, in.readAllBytes());
        } catch (IOException e) {
            throw new CryptoException(e);
        }
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

    /**
     * 根据算法和 {@link KeySpec} 创建私钥
     */
    public static PrivateKey getPrivateKey(String algorithm, KeySpec keySpec) {
        try {
            return getKeyFactory(algorithm).generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 根据算法和 {@link KeySpec} 创建公钥
     */
    public static PublicKey getPublicKey(String algorithm, KeySpec keySpec) {
        try {
            return getKeyFactory(algorithm).generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 根据算法创建 KeyFactory
     */
    public static KeyFactory getKeyFactory(String algorithm) {
        algorithm = getMainAlgorithm(algorithm);
        try {
            return KeyFactory.getInstance(algorithm, GlobalProvider.INSTANCE.getProvider());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 获取密钥主体算法
     */
    public static String getMainAlgorithm(String algorithm) {
        final int slashIndex = algorithm.indexOf('/');
        if (slashIndex > 0) {
            return algorithm.substring(0, slashIndex);
        }
        return algorithm;
    }
}
