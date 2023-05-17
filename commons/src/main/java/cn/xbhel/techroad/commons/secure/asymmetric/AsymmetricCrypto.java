package cn.xbhel.techroad.commons.secure.asymmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * 非对称加密算法
 * 1. 加密：使用公钥加密，私钥解密；保证只有私钥的拥有者才能进行解密，其他人无法获得信息，但是无法保证信息不被篡改.
 * 2. 签名：使用私钥加密，公钥解密；可以防止私钥所有者发布的信息被伪造和篡改，但是无法保证信息不被获得.
 *
 * @author xbhel
 */
public class AsymmetricCrypto {

    /** 常量用于决定要使用的密钥：私钥 */
    public static final int PRIVATE_KEY_TYPE = 0x01;
    /** 常量用于决定要使用的密钥：公钥 */
    public static final int PUBLIC_KEY_TYPE = 0x02;

    protected Cipher cipher;
    protected PublicKey publicKey;
    protected PrivateKey privateKey;
    protected int encryptBlockSize = -1;
    protected int decryptBlockSize = -1;

    /**
     * 创建时可以私钥和公钥可以仅传递其中一个，此时只能用来解密或加密，也可以在后续调用 set 方法进行赋值.
     *
     * @param algorithm  算法
     * @param publicKey  私钥
     * @param privateKey 公钥
     */
    protected AsymmetricCrypto(String algorithm, PublicKey publicKey, PrivateKey privateKey) {
        this.cipher = initCipher(algorithm);
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }


    public byte[] decrypt(byte[] data, int keyType) {
        try {
            // 使用 key 初始化 Cipher
            this.cipher.init(Cipher.DECRYPT_MODE, getKey(keyType));
            this.decryptBlockSize = getBlockSize(this.decryptBlockSize);
            if (this.decryptBlockSize > 0) {
                return doFinalWithBlock(this.decryptBlockSize, data);
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] encrypt(byte[] data, int keyType) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, getKey(keyType));
            this.encryptBlockSize = getBlockSize(this.encryptBlockSize);
            if (this.encryptBlockSize > 0) {
                return doFinalWithBlock(this.encryptBlockSize, data);
            }
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 对于块密码加密算法(如 RSA)，其以 block(块) 的形式组织数据转换(加密|解密)，且每次只能转换 <= blockSize 长度的数据，
     * 因此对于超过 blockSize 大小的数据，必须先将拆分成多个块，然后进行多次转换。blockSize的大小与 Key(密钥)的长度和算法的填充方式有关.
     * 当转换的数据长度超过 blockSize 时会抛出 ArrayIndexOutOfBoundsException 异常.
     */
    private int getBlockSize(int blockSize) {
        // 引入 BouncyCastle 能够自动获取. Cipher默认情况下获取不到大部分算法的块大小，引入 BouncyCastle 后可以解决这一点.
        // 获取算法每个密码块的大小(一组固定长度的位)，返回 0 则表示算法不是块加密算法.
        if (blockSize < 0) {
            return this.cipher.getBlockSize();
        }
        return blockSize;
    }

    private byte[] doFinalWithBlock(int blockSize, byte[] data) throws
            IOException, IllegalBlockSizeException, BadPaddingException {
        var dataLen = data.length;
        // 块数量
        var blockNum = (int) Math.ceil((double) dataLen / blockSize);
        // 为指定长度的输入数据执行 doFinal/update 操作后返回的字节数组的大小，但执行操作时实际输出的长度可能会比这个值小.
        var perBlockOutputDataSize = this.cipher.getOutputSize(blockSize);
        // 组织输出
        try (var bytesStream = new ByteArrayOutputStream(blockNum * perBlockOutputDataSize)) {
            while (blockNum-- > 0) {
                var offset = blockNum * blockSize;
                // 要转换的数据长度
                var len = Math.min(dataLen - offset, blockSize);
                // 写入
                bytesStream.write(this.cipher.doFinal(data, offset, len));
            }
            return bytesStream.toByteArray();
        }
    }

    public Key getKey(int keyType) {
        if (Objects.equals(PRIVATE_KEY_TYPE, keyType)) {
            return Objects.requireNonNull(this.privateKey,
                    "Private key must be not null when use it.");
        }
        if (Objects.equals(PUBLIC_KEY_TYPE, keyType)) {
            return Objects.requireNonNull(this.publicKey,
                    "Public key must be not null when use it.");
        }
        throw new IllegalArgumentException("Unsupported key type: " + keyType);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    private static Cipher initCipher(String algorithm) {
        try {
            return Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }
}
