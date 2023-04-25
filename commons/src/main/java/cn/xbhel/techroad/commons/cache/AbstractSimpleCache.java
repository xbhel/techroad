package cn.xbhel.techroad.commons.cache;

import java.util.Iterator;
import java.util.Map;

/**
 * 抽象类，缓存的简单实现
 * @author xbhel
 */
public abstract class AbstractSimpleCache<K, V> implements Cache<K, V> {

    private static final long serialVersionUID = 1L;

    private final Map<K, V> cacheMap;

    protected AbstractSimpleCache(Map<K, V> cacheMap) {
        this.cacheMap = cacheMap;
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

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return this.cacheMap.entrySet().iterator();
    }
}
