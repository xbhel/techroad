package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;
import cn.xbhel.techroad.commons.util.ByteUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AESTest {

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
    void createByAlgorithmAndKey() {
        var aes = new AES("AES", KeyUtils.getKey("AES", 256));
        assertThat(aes).isNotNull();
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
        assertThat(encrypt).isNotEmpty();
    }

}