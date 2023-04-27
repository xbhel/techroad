package cn.xbhel.techroad.commons.yaml;

import java.io.Serializable;

public interface PropHandler extends Serializable {
    /**
     * 实现该方法对 value 进行处理，并返回处理后的值
     * @param propValue 属性的值
     * @return 处理后的值
     */
    String handle(String propValue);

    /**
     * 如果返回 true，则调用 {@code handle()} 进行处理
     * @param propValue 原始值
     * @return 处理后的值
     */
    boolean isSupport(String propValue);
}
