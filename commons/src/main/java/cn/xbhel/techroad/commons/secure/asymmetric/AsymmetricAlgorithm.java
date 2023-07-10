package cn.xbhel.techroad.commons.secure.asymmetric;

import javax.crypto.Cipher;
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

    RSA("RSA", 11, 1024),

    RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding", 11, 1024),

    /**
     * Recommended for RSA;
     * paddingSize: 66; 使用 -1 表示自动获取
     */
    RSA_NONE_OAEP("RSA/None/OAEPWITHSHA-256ANDMGF1PADDING", -1, 1024),

    /**
     * the ECB mode can be used for RSA when "None" is not available with the security provider used -
     * in that case, ECB will be treated as "None" for RSA.
     * paddingSize: 66; 使用 -1 表示自动获取
     */
    RSA_ECB_OAEP("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING", 66, 1024);

    /**
     * key: algorithmName, value: AsymmetricAlgorithm
     */
    private static final Map<String, AsymmetricAlgorithm> nameWithEnumMap;

    static {
        nameWithEnumMap = Arrays.stream(AsymmetricAlgorithm.values())
                .collect(Collectors.toMap(AsymmetricAlgorithm::getName,
                        Function.identity()));
    }

    /**
     * 算法名称
     */
    private final String name;

    /**
     * 加密块填充字节的长度，用于计算加密块大小，-1 表示自动获取，无需计算，
     * 将调用 {@link Cipher#getBlockSize()} 进行获取
     */
    private final int paddingSize;

    /**
     * 算法密钥长度
     */
    private final int keySize;

    AsymmetricAlgorithm(String name, int paddingSize, int keySize) {
        this.name = name;
        this.paddingSize = paddingSize;
        this.keySize = keySize;
    }

    public static Optional<AsymmetricAlgorithm> get(String algorithmName) {
        return Optional.ofNullable(nameWithEnumMap.get(algorithmName));
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
