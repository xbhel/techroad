package cn.xbhel.techroad.commons.cache.impl;

import cn.xbhel.techroad.commons.cache.AbstractSimpleCache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU(least recently used) 缓存，基于 LinkedHashMap 访问顺序(access-order) 特性实现，
 * LinkedHashMap 中的 key 每被访问一次，这个 key 就会移动到链表头部. 这是一个线程不安全的类.
 *
 * @author xbhel
 */
public class LRUCache<K, V> extends AbstractSimpleCache<K, V> {

    private static final long serialVersionUID = 1L;

    public LRUCache(final int maxSize) {
        super(new LinkedHashMap<>(maxSize + 1, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > maxSize;
            }
        });
    }

}
