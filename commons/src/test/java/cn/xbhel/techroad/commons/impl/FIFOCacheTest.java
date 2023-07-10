package cn.xbhel.techroad.commons.impl;

import cn.xbhel.techroad.commons.cache.impl.FIFOCache;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FIFOCacheTest {

    @Test
    void create() {
        var cache = new FIFOCache<String, String>(20);
        assertThat(cache).isNotNull();
    }

}