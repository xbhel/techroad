package cn.xbhel.techroad.commons.secure;

/**
 * 非对此加密算法枚举类
 * @author xbhel
 */
public enum AsymmetricAlgorithm {

    RSA("RSA", 11);

    /**
     * 算法名称
     */
    private final String name;
    /**
     * 加密块填充字节的长度
     */
    private final int paddingSize;

    AsymmetricAlgorithm(String name, int paddingSize) {
        this.name = name;
        this.paddingSize = paddingSize;
    }

    public String getName() {
        return name;
    }

    public int getPaddingSize() {
        return paddingSize;
    }
}
