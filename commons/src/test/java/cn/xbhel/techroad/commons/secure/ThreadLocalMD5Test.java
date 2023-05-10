package cn.xbhel.techroad.commons.secure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadLocalMD5Test {

    @Test
    void shouldCorrectVerifyCheckSum() {
        var checksum = "35454b055cc325ea1af2126e27707052";
        var result = ThreadLocalMD5.get()
                .digestToHex("ILoveJava".getBytes(StandardCharsets.UTF_8));
        assertThat(result).isEqualTo(checksum);
    }

    @AfterEach
    void destroy() {
        ThreadLocalMD5.remove();
    }

}