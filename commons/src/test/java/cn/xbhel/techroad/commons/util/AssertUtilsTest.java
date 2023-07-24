package cn.xbhel.techroad.commons.util;

import org.junit.jupiter.api.Test;

import static cn.xbhel.techroad.commons.util.AssertUtils.notNull;
import static org.assertj.core.api.Assertions.assertThatCode;

class AssertUtilsTest {

    @Test
    void testNotNull() {
        assertThatCode(() -> notNull("null", null))
        .isInstanceOf(IllegalArgumentException.class);
    }

}