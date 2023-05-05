package cn.xbhel.techroad.commons.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class MD5UtilsTest {

    @Test
    void givenParagraph_whenEncode_thenVerifyCheckSum() {
        var checksum = "35454b055cc325ea1af2126e27707052";
        var md5Data = MD5Utils.encode("ILoveJava".getBytes(StandardCharsets.UTF_8));
        assertThat(new String(HexUtils.encode(md5Data)))
                .isEqualTo(checksum);
    }

    @AfterEach
    void destroy() {
        MD5Utils.removeDigester();
    }

}