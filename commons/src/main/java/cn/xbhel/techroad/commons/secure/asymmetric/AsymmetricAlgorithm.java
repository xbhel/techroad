package cn.xbhel.techroad.commons.secure.asymmetric;

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
public enum AsymmetricAlgorithm {

    RSA("RSA", 11, 1024);

    /**
     * key: algorithmName, value: AsymmetricAlgorithm
     */
    private static final Map<String, AsymmetricAlgorithm> stateMap;

    static {
        stateMap = Arrays.stream(AsymmetricAlgorithm.values())
                .collect(Collectors.toMap(AsymmetricAlgorithm::getName,
                        Function.identity()));
    }

    /**
     * 算法名称
     */
    private final String name;

    /**
     * 加密块填充字节的长度
     */
    private final int paddingSize;

    /**
     * 密钥长度
     */
    private final int keySize;

    AsymmetricAlgorithm(String name, int paddingSize, int keySize) {
        this.name = name;
        this.paddingSize = paddingSize;
        this.keySize = keySize;
    }

    public static Optional<AsymmetricAlgorithm> get(String algorithmName) {
        return Optional.ofNullable(stateMap.get(algorithmName));
    }

    public String getName() {
        return name;
    }

    public int getPaddingSize() {
        return paddingSize;
    }

    public int getKeySize() {
        return keySize;
    }
}
