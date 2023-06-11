package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.CryptoException;
import cn.xbhel.techroad.commons.secure.GlobalProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * 对称加密
 * @author xbhel
 */
public abstract class AbstractSymmetricCrypto implements SymmetricCrypto{

    protected Cipher cipher;
    protected SecretKey key;

    protected AbstractSymmetricCrypto(String algorithm, SecretKey key) {
        this.cipher = initCipher(algorithm);
        this.key = key;
    }

    private static Cipher initCipher(String algorithm) {
        try {
            return Cipher.getInstance(algorithm, GlobalProvider.INSTANCE.getProvider());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CryptoException(e);
        }
    }
}
