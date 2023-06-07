package cn.xbhel.techroad.commons.secure.asymmetric;

import cn.xbhel.techroad.commons.secure.KeyUtils;
import org.junit.jupiter.api.Test;

import static cn.xbhel.techroad.commons.secure.asymmetric.AsymmetricParam.of;
import static org.assertj.core.api.Assertions.assertThat;

class RASTest {

    @Test
    void shouldCreate_RSA() {
        assertCreateRSA(AsymmetricAlgorithm.RSA);
    }

    @Test
    void shouldCreate_RSA_ECB_PKCS1(){
        assertCreateRSA(AsymmetricAlgorithm.RSA_ECB_PKCS1);
    }

    private void assertCreateRSA(AsymmetricAlgorithm algorithm) {
        // 1
        var rsa1 = new RAS(of(algorithm));
        assertThat(rsa1).isNotNull();

        // 2
        var rsa2 = new RAS(of(algorithm.getName(), algorithm.getKeySize()));
        assertThat(rsa2).isNotNull();

        // 3
        var keyPair = KeyUtils.getKeyPair(algorithm.getName(), algorithm.getKeySize());
        var rsa3 = new RAS(of(algorithm.getName(), keyPair.getPublic(), keyPair.getPrivate()));
        assertThat(rsa3).isNotNull();
    }

}