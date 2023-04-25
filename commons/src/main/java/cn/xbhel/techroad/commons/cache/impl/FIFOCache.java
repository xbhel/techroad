package cn.xbhel.techroad.commons.cache.impl;

import cn.xbhel.techroad.commons.cache.AbstractSimpleCache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FIFO(First In First Out) 缓存.
 * 基于 LinkedHashMap 插入顺序(insert order) 特性实现，添加已存在的 key 不会改变缓存元素的位置.
 * 这是一个线程不安全的类，因为基于 LinkedHashMap 实现，.
 * <p>
 * 可以直接 extends LinkedHashMap 实现，这样的一个好处是功能性方法更多.
 *
 * @author xbhel
 */
public class FIFOCache<K, V> extends AbstractSimpleCache<K, V> {

    public FIFOCache(final int maxSize) {
        // initialCapacity = maxSize + 1 是为了避免当满足触发条件时导致扩容.
        // loadFactor = 1 也是避免当达到 0.75 时就发生扩容，我们缓存的大小是固定的，不需要扩容.
        super(new LinkedHashMap<>(maxSize + 1, 1) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > maxSize;
            }
        });
    }

}
