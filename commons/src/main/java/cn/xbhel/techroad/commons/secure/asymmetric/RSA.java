package cn.xbhel.techroad.commons.secure.asymmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;

import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.function.Consumer;

/**
 * RSA 加密算法
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 *
 * @author xbhel
 */
public class RSA extends AsymmetricCryptoImpl {

    /**
     * 创建时可以私钥和公钥可以仅传递其中一个，此时只能用来解密或加密，也可以在后续调用 set 方法进行赋值.
     *
     * @param param 非对称加密属性对象
     */
    public RSA(AsymmetricParam param) {
        super(param);
    }

    @Override
    public byte[] decrypt(byte[] data, KeyType keyType) {
        if (this.decryptBlockSize < 0) {
            processBlockSizeByPreDefined(o -> {
                var key = (RSAKey) getKey(keyType);
                // 计算 rsa 解密块大小：密钥的长度 / 8
                this.decryptBlockSize = key.getModulus().bitLength() / 8;
            });
        }
        return super.decrypt(data, keyType);
    }

    @Override
    public byte[] encrypt(byte[] data, KeyType keyType) {
        if (this.encryptBlockSize < 0) {
            // 计算 rsa 加密块大小：密钥的长度 / 8 (一个字节8位) - 加密块填充字节的长度
            processBlockSizeByPreDefined(o -> {
                var key = (RSAKey) getKey(keyType);
                this.encryptBlockSize = key.getModulus().bitLength() / 8 - o.getPaddingSize();
            });
        }
        return super.encrypt(data, keyType);
    }

    /**
     * 根据模数和指数生成私钥
     *
     * @param modules  RSA 模数
     * @param exponent RSA 指数
     */
    public static RSAPrivateKey getPrivateKey(byte[] modules, byte[] exponent) {
        var privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modules), new BigInteger(exponent));
        return (RSAPrivateKey) KeyUtils.getPrivateKey(AsymmetricAlgorithm.RSA.getName(), privateKeySpec);
    }

    /**
     * 根据模数和指数生成公钥
     *
     * @param modules  RSA 模数
     * @param exponent RSA 指数
     */
    public static RSAPublicKey generatePublicKey(byte[] modules, byte[] exponent) {
        var publicKeySpec = new RSAPublicKeySpec(new BigInteger(modules), new BigInteger(exponent));
        return (RSAPublicKey) KeyUtils.getPublicKey(AsymmetricAlgorithm.RSA.getName(), publicKeySpec);
    }

    private void processBlockSizeByPreDefined(Consumer<AsymmetricAlgorithm> consumer) {
        var algorithm = AsymmetricAlgorithm.get(cipher.getAlgorithm());
        algorithm.filter(o -> o.getPaddingSize() > 0).ifPresent(consumer);
    }

}
