package cn.xbhel.techroad.commons.secure.symmetric;

import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.xbhel.techroad.commons.secure.KeyUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * AES（Advanced Encryption Standard）
 * AES 是目前最常用的对称加密算法之一。它支持不同的密钥长度（128、192 或 256 位），
 * 并被广泛用于保护敏感数据的安全性。AES 算法具有较高的安全性和性能，在许多应用中被广泛采用，
 * 包括网络通信、数据存储等领域。
 *
 * @author xbhel
 */
public class AES extends SymmetricCryptoImpl {

    public AES(String algorithm, SecretKey key) {
        super(algorithm, key);
    }

    public AES(String algorithm, SecretKey key, byte[] iv) {
        super(algorithm, key);
        if (PrimitiveArrayUtil.isNotEmpty(iv)) {
            setAlgorithmParameterSpec(new IvParameterSpec(iv));
        }
    }

    public AES(String algorithm, int keySize) {
        super(algorithm, KeyUtils.getKey(algorithm, keySize));
    }

    public AES(SymmetricAlgorithm algorithm) {
        super(algorithm);
    }
}
