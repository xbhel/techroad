package cn.xbhel.techroad.commons.secure.symmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;
import org.junit.jupiter.api.Test;

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

}