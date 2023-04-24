package cn.xbhel.techroad.commons.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU(least recently used) 缓存，基于 LinkedHashMap 访问顺序(access-order) 特性实现，
 * LinkedHashMap 中的 key 每被访问一次，这个 key 就会移动到链表头部. 这是一个线程不安全的类.
 *
 * @author xbhel
 */
public class LRUCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cacheMap;

    public LRUCache(final int maxSize) {
        this.cacheMap = new LinkedHashMap<>(maxSize + 1, 1, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > maxSize;
            }
        };
    }

    @Override
    public void put(K key, V value) {
        this.cacheMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.cacheMap.get(key);
    }

    @Override
    public boolean remove(K key) {
        return this.cacheMap.remove(key) != null;
    }

    @Override
    public boolean exists(K key) {
        return this.cacheMap.containsKey(key);
    }

    @Override
    public int size() {
        return this.cacheMap.size();
    }

    @Override
    public void clear() {
        this.cacheMap.clear();
    }
}
