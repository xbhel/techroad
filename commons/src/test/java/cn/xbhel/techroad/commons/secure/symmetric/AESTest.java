package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;
import cn.xbhel.techroad.commons.util.ByteUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AESTest {

    /**
     * Random Number Generator (RNG) algorithm.
     * SHA1-PRNG, PRNG(伪随机数生产器): Pseudorandom number generator
     */
    private static final String SHA1PRNG = "SHA1PRNG";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    @Test
    void createByEnum() {
        var aes = new AES(SymmetricAlgorithm.AES);
        assertThat(aes).isNotNull();
    }

    @Test
    void createByAlgorithmAndKeySize() {
        var aes = new AES("AES", 128);
        assertThat(aes).isNotNull();
    }

    @Test
    void createByAlgorithmAndKey() throws NoSuchAlgorithmException {
        var aes = new AES("AES", KeyUtils.getKey("AES", 256));
        var aesBySHA1PRNG = new AES("AES",
                KeyUtils.getKey("AES", 256, SecureRandom.getInstance(SHA1PRNG)));
        assertThat(aes).isNotNull();
        assertThat(aesBySHA1PRNG).isNotNull();
    }

    @Test
    void aesEncryption() {
        var aes = new AES(SymmetricAlgorithm.AES);
        var encrypt = aes.encrypt(ByteUtils.toBytes("this is a piece of text."));
        assertThat(encrypt).isNotEmpty();
    }

    @Test
    void aesEncryptLargeText() {
        var aes = new AES(SymmetricAlgorithm.AES);
        var largeText = Stream.generate(() -> "this is a piece of text.")
                .limit(5000)
                .collect(Collectors.joining());
        var encrypt = aes.encrypt(ByteUtils.toBytes(largeText));
        var decrypt = aes.decrypt(encrypt);
        assertThat(encrypt).isNotEmpty();
        assertThat(ByteUtils.toString(decrypt)).isEqualTo(largeText);
    }

    @Test
    void aes_gcm_nopadding() {
        var aes = new AES(SymmetricAlgorithm.AES_GCM_NOPADDING);
        // gcm 需要设置 algorithmParameterSpec 才能正常解密
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, new byte[GCM_IV_LENGTH]);
        aes.setAlgorithmParameterSpec(gcmParameterSpec);

        var largeText = Stream.generate(() -> "this is a piece of text.")
                .limit(5000)
                .collect(Collectors.joining());
        var encrypt = aes.encrypt(ByteUtils.toBytes(largeText));
        var decrypt = aes.decrypt(encrypt);
        assertThat(encrypt).isNotEmpty();
        assertThat(ByteUtils.toString(decrypt)).isEqualTo(largeText);
    }

    @Test
    void useSpecialKey() throws NoSuchAlgorithmException {
        // 创建 key
        byte[] newKey = ByteUtils.toBytes("hello world!");
        MessageDigest sha = MessageDigest.getInstance("SHA-512");
        newKey = sha.digest(newKey);
        // 密钥的关键是密钥的长度满足算法密钥的长度
        // 16 byte = 16 * 8 = 128 位
        // 刚好满足 AES key 的长度
        newKey = Arrays.copyOf(newKey, 16);
        SecretKeySpec keySpec =  new SecretKeySpec(newKey, "AES");

        var aes = new AES(SymmetricAlgorithm.AES.getName(), keySpec);
        var largeText = Stream.generate(() -> "this is a piece of text.")
                .limit(5000)
                .collect(Collectors.joining());
        var encrypt = aes.encrypt(ByteUtils.toBytes(largeText));
        var decrypt = aes.decrypt(encrypt);
        assertThat(encrypt).isNotEmpty();
        assertThat(ByteUtils.toString(decrypt)).isEqualTo(largeText);
    }

}