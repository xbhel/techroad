package cn.xbhel.techroad.commons.secure.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;

/**
 * RSA 加密算法
 *
 * @author xbhel
 */
public class RAS extends BlockAsymmetricOperator {

    /**
     * 创建时可以私钥和公钥可以仅传递其中一个，此时只能用来解密或加密，也可以在后续调用 set 方法进行赋值.
     *
     * @param algorithm  算法
     * @param publicKey  私钥
     * @param privateKey 公钥
     */
    public RAS(String algorithm, PublicKey publicKey, PrivateKey privateKey) {
        super(algorithm, publicKey, privateKey);
    }

    @Override
    public byte[] encrypt(byte[] data, int keyType) {
        if (this.encryptBlockSize < 0) {
            var algorithm = AsymmetricAlgorithm.get(this.cipher.getAlgorithm());
            algorithm.ifPresent(o -> {
                var key = (RSAKey) getKey(keyType);
                // 计算 rsa 加密块大小：密钥的长度 / 8 (一个字节8位) - 加密块填充字节的长度
                this.encryptBlockSize = key.getModulus().bitLength() / 8 - o.getPaddingSize();
            });
        }
        return super.encrypt(data, keyType);
    }

    @Override
    public byte[] decrypt(byte[] data, int keyType) {
        if (this.decryptBlockSize < 0) {
            var key = (RSAKey) getKey(keyType);
            // 计算 rsa 解密块大小：密钥的长度 / 8
            this.decryptBlockSize = key.getModulus().bitLength() / 8;
        }
        return super.decrypt(data, keyType);
    }

}
