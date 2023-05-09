package cn.xbhel.techroad.commons.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class MD5UtilsTest {

    @Test
    void givenParagraph_thenVerifyCheckSum() {
        var checksum = "35454b055cc325ea1af2126e27707052";
        var result = MD5Utils.digestToHex("ILoveJava".getBytes(StandardCharsets.UTF_8));
        assertThat(result)
                .isEqualTo(checksum);
    }

    @AfterEach
    void destroy() {
        MD5Utils.removeDigesterFromFactory();
    }

}