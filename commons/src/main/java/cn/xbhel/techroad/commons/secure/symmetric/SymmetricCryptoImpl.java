package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.CryptoException;
import cn.xbhel.techroad.commons.secure.KeyUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * 对称加密
 *
 * @author xbhel
 */
public class SymmetricCryptoImpl extends AbstractSymmetricCrypto {

    public SymmetricCryptoImpl(String algorithm, SecretKey key) {
        super(algorithm, key);
    }

    public SymmetricCryptoImpl(String algorithm, int keySize) {
        this(algorithm, KeyUtils.getKey(algorithm, keySize));
    }

    public SymmetricCryptoImpl(SymmetricAlgorithm algorithm) {
        this(algorithm.getName(), algorithm.getKeySize());
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.key);
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.key);
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
