package cn.xbhel.techroad.commons.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ByteUtilsTest {

    @Test
    void toBytes() {
        byte[] bytes = ByteUtils.toBytes(1678);
        assertThat(bytes).isEqualTo(new byte[]{-114, 6, 0, 0});
    }

    @Test
    void toInt() {
        int i = ByteUtils.toInt(new byte[]{-114, 6, 0, 0});
        assertThat(i).isEqualTo(1678);

    }
}