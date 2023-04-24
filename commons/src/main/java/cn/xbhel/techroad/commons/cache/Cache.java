package cn.xbhel.techroad.commons.cache;

/**
 * 定义一个缓存接口，支持缓存的 CRUD
 *
 * @param <K> 缓存 key 的类型
 * @param <V> 缓存 value 的类型
 * @author xbhel
 */
public interface Cache<K, V> {
    /**
     * 添加元素至缓存中
     */
    void put(K key, V value);

    /**
     * 通过 key 获取缓存
     */
    V get(K key);

    /**
     * 通过 key 删除缓存
     */
    boolean remove(K key);

    /**
     * 判断 key 是否存在缓存中
     */
    boolean exists(K key);

    /**
     * 获取缓存的大小
     */
    int size();

    /**
     * 清空缓存
     */
    void clear();
}
