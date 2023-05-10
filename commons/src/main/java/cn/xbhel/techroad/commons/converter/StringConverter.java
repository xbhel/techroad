package cn.xbhel.techroad.commons.converter;

/**
 * 定义一个转换器接口用于在字符床和不同对象之间互相转换。
 * @author xbhel
 * @param <T> 转换为 String 的对象
 */
public interface StringConverter<T> {
    /**
     * 将字符串转换为对象
     */
    T fromString(String str);

    /**
     * 将对象转换为字符串
     */
    String toString(T object);
}
