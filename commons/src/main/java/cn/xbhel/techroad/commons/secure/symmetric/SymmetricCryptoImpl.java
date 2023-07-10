package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.CryptoException;
import cn.xbhel.techroad.commons.secure.KeyUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 对称加密
 *
 * @author xbhel
 */
public class SymmetricCryptoImpl extends AbstractSymmetricCrypto {

    private AlgorithmParameterSpec algorithmParameterSpec;

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
            initCipher(Cipher.DECRYPT_MODE);
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            initCipher(Cipher.ENCRYPT_MODE);
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    private void initCipher(int mode) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.algorithmParameterSpec != null) {
            this.cipher.init(mode, this.key, algorithmParameterSpec);
        } else {
            this.cipher.init(mode, this.key);
        }
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return algorithmParameterSpec;
    }

    public void setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
        this.algorithmParameterSpec = algorithmParameterSpec;
    }
}
