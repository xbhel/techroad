package cn.xbhel.techroad.commons.secure.symmetric;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 非对此加密算法枚举类
 *
 * @author xbhel
 */
public enum SymmetricAlgorithm {

    AES("AES", 128);

    /**
     * 算法名称
     */
    private final String name;

    /**
     * 算法密钥长度
     */
    private final int keySize;

    SymmetricAlgorithm(String name, int keySize) {
        this.name = name;
        this.keySize = keySize;
    }

    public String getName() {
        return name;
    }

    public int getKeySize() {
        return keySize;
    }
}
