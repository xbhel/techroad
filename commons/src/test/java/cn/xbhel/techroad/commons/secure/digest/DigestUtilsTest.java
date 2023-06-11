package cn.xbhel.techroad.commons.secure.digest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DigestUtilsTest {

    @Test
    void shouldCreateIfAlgorithmSupported() {
        var sha256 = DigestUtils.of("SHA-256");
        var md5 = DigestUtils.of("MD5");
        assertThat(sha256).isNotNull();
        assertThat(md5).isNotNull();
    }

}