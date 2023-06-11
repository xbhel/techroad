package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.CryptoException;
import cn.xbhel.techroad.commons.secure.KeyUtils;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES（Advanced Encryption Standard）
 * AES 是目前最常用的对称加密算法之一。它支持不同的密钥长度（128、192 或 256 位），
 * 并被广泛用于保护敏感数据的安全性。AES 算法具有较高的安全性和性能，在许多应用中被广泛采用，
 * 包括网络通信、数据存储等领域。
 * @author xbhel
 */
public class AES extends SymmetricCryptoImpl {

    /**
     * Random Number Generator (RNG) algorithm.
     * SHA1-PRNG, PRNG(伪随机数生产器): Pseudorandom number generator
     */
    private static final String SHA1PRNG = "SHA1PRNG";

    public AES(String algorithm, SecretKey key) {
        super(algorithm, key);
    }

    public AES(String algorithm, int keySize) {
        super(algorithm, getKey(algorithm, keySize));
    }

    public AES(SymmetricAlgorithm algorithm) {
        this(algorithm.getName(), algorithm.getKeySize());
    }

    private static SecretKey getKey(String algorithm, int keySize) {
        try {
            return KeyUtils.getKey(algorithm, keySize, SecureRandom.getInstance(SHA1PRNG));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }
}
