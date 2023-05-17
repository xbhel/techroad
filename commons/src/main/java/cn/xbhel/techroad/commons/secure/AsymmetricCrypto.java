package cn.xbhel.techroad.commons.secure;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 非对称加密
 * https://github.com/dromara/hutool/issues/721
 *
 * @author xbhel
 */
public class AsymmetricCrypto {

    protected Cipher cipher;
    protected PrivateKey privateKey;
    protected PublicKey publicKey;
    protected int encryptBlockSize = -1;
    protected int decryptBlockSize = -1;

    public AsymmetricCrypto(String algorithm, PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.cipher = initCipher(algorithm);
    }

    public byte[] decrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, privateKey);
            if (this.decryptBlockSize < 0) {
                this.decryptBlockSize = this.cipher.getBlockSize();
            }
            if (this.decryptBlockSize > 0) {
                return doFinalWithBlock(this.decryptBlockSize, data);
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] encrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            if (this.encryptBlockSize < 0) {
                this.encryptBlockSize = this.cipher.getBlockSize();
            }
            if (this.encryptBlockSize > 0) {
                return doFinalWithBlock(this.encryptBlockSize, data);
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
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

    private static Cipher initCipher(String algorithm) {
        try {
            return Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }
}
