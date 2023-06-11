package cn.xbhel.techroad.commons.cache.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

    @Test
    void create() {
        var cache = new LRUCache<String, String>(20);
        assertThat(cache).isNotNull();
    }

}