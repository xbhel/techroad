package cn.xbhel.techroad.commons.converter;

/**
 * 定义一个转换器接口用于在字节数组和不同对象之间互相转换。
 * @author xbhel
 * @param <T> 转换为 byte[] 的对象
 */
public interface ByteArrayConverter<T> {
    /**
     * 将对象转换为字节数组
     */
    byte[] toByteArray(T object);

    /**
     * 将字节数组转换为对象
     */
    T fromByteArray(byte[] bytes);
}
