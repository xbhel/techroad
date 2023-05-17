package cn.xbhel.techroad.commons.secure.asymmetric;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 非对此加密算法枚举类
 * @author xbhel
 */
public enum AsymmetricAlgorithm {

    RSA("RSA", 11);

    /** key: algorithmName, value: AsymmetricAlgorithm */
    private static final Map<String, AsymmetricAlgorithm> NAME_MAP = Arrays
            .stream(AsymmetricAlgorithm.values())
            .collect(Collectors.toMap(AsymmetricAlgorithm::getName, Function.identity()));
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

    public static Optional<AsymmetricAlgorithm> get(String algorithmName) {
        return Optional.ofNullable(NAME_MAP.get(algorithmName));
    }

    public String getName() {
        return name;
    }

    public int getPaddingSize() {
        return paddingSize;
    }
}
