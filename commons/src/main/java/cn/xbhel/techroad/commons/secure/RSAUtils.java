package cn.xbhel.techroad.commons.secure;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.Security;

/**
 * @author xbhel
 */
public final class RSAUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private RSAUtils() {
    }

    /**
     * 加密：通常使用公钥加密，这样保证只有私钥的拥有者才能进行解密，其他人无法解密。
     * 签名：通常使用私钥进行签名，这样保证别人无法进行伪造，只有私钥拥有者才能进行签名。
     */
    public static byte[] encrypt(String transformation, byte[] data, Key key) {
        return transform(transformation, data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密：使用私钥解密，保证只有私钥拥有者才能看到数据。
     * 验签：使用公钥验签，保证是对应的私钥进行的签名，没有被伪造。
     */
    public static byte[] decrypt(String transformation, byte[] data, Key key) {
        return transform(transformation, data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * {@code Cipher.getBlockSize} 返回值为 0，此时需要自己根据算法名称进行判断计算，可以维护一个常量类去记录算法的加密/解密块大小，
     * 或者可以引入 BouncyCastle，此时 {@code Cipher.getBlockSize} 可以正确获取到，BouncyCastle 维护了常用算法的块大小。
     * 相关 issue：https://github.com/dromara/hutool/issues/721
     */
    private static byte[] transform(String transformation, byte[] data, Key key, int mode) {
        try {
            var cipher = Cipher.getInstance(transformation, BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(mode, key);

            // 对于块密码加密算法(如 AES)，其以 block 的形式组织数据转换(加密/解密)，每次只能转换一个 block 的数据，
            // 当加密数据长度超过加密块的大小会抛出 ArrayIndexOutOfBoundsException 异常，
            // 因此对于超过 block 大小的数据，必须先将拆分成多个块，然后进行多次转换.

            // 获取算法每个密码块的大小(一组固定长度的位)，返回 0 则表示算法不是块密码加密算法
            var blockSize = cipher.getBlockSize();

            if (blockSize == 0) {
                return cipher.doFinal(data);
            }

            var dataLen = data.length;
            // 块数量
            var blockNum = (int) Math.ceil((double) dataLen / blockSize);
            // 为指定长度的输入数据执行 doFinal/update 操作后返回的字节数组的大小，但执行操作时实际输出的长度可能会比这个值小.
            var perBlockOutputDataSize = cipher.getOutputSize(blockSize);
            // 组织输出
            try (var bytesStream = new ByteArrayOutputStream(blockNum * perBlockOutputDataSize)) {
                while (blockNum-- > 0) {
                    var offset = blockNum * blockSize;
                    // 要转换的数据长度
                    var len = Math.min(dataLen - offset, blockSize);
                    // 写入
                    bytesStream.write(cipher.doFinal(data, offset, len));
                }
                return bytesStream.toByteArray();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
