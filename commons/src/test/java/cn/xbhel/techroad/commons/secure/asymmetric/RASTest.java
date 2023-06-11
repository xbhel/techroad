package cn.xbhel.techroad.commons.secure.asymmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;
import cn.xbhel.techroad.commons.util.ByteUtils;
import org.junit.jupiter.api.Test;

import static cn.xbhel.techroad.commons.secure.asymmetric.AsymmetricParam.of;
import static org.assertj.core.api.Assertions.assertThat;

class RASTest {

    @Test
    void createByEnum() {
        var rsa = new RAS(of(AsymmetricAlgorithm.RSA));
        assertThat(rsa).isNotNull();
    }

    @Test
    void createByAlgorithmAndKeySize() {
        var rsa = new RAS(of("RSA", 2048));
        assertThat(rsa).isNotNull();
    }

    @Test
    void createByAlgorithmAndKey() {
        var keyPair = KeyUtils.getKeyPair("RSA", 2048);
        var rsa = new RAS(of("RSA", keyPair.getPublic(), keyPair.getPrivate()));
        assertThat(rsa).isNotNull();
    }

    @Test
    void rsa() {
        var rsa = new RAS(of(AsymmetricAlgorithm.RSA));
        assertThat(rsa.getPrivateKey()).isNotNull();
        assertThat(rsa.getPublicKey()).isNotNull();

        // 加密
        byte[] encryptData = rsa.encrypt(ByteUtils.toBytes("【山丘】李宗盛 - 我没有刻意隐藏，也无意让你感伤."),
                AsymmetricCrypto.KeyType.PUBLIC_KEY);
        assertThat(encryptData).isNotEmpty();

        // 解密
        byte[] decryptData = rsa.decrypt(encryptData, AsymmetricCrypto.KeyType.PRIVATE_KEY);
        assertThat(ByteUtils.toString(decryptData)).isEqualTo("【山丘】李宗盛 - 我没有刻意隐藏，也无意让你感伤.");
    }

    @Test
    void rsa_ecb_pkcs1() {
        var rsa = new RAS(of("RSA/ECB/PKCS1Padding", 2048));
        assertThat(rsa.getPrivateKey()).isNotNull();
        assertThat(rsa.getPublicKey()).isNotNull();

        // 加密
        byte[] encryptData = rsa.encrypt(ByteUtils.toBytes("【山丘】李宗盛 - 我没有刻意隐藏，也无意让你感伤."),
                AsymmetricCrypto.KeyType.PUBLIC_KEY);
        assertThat(encryptData).isNotEmpty();

        // 解密
        byte[] decryptData = rsa.decrypt(encryptData, AsymmetricCrypto.KeyType.PRIVATE_KEY);
        assertThat(ByteUtils.toString(decryptData)).isEqualTo("【山丘】李宗盛 - 我没有刻意隐藏，也无意让你感伤.");
    }
}