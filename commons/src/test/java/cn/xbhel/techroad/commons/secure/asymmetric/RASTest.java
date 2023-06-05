package cn.xbhel.techroad.commons.secure.asymmetric;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RASTest {

    @Test
    void createRSA() {
        var rsa1 = new RAS(AsymmetricProps.of(AsymmetricAlgorithm.RSA));
        var rsa2 = new RAS(AsymmetricProps.of(AsymmetricAlgorithm.RSA.getName(),
                AsymmetricAlgorithm.RSA.getKeySize()));
        assertThat(rsa1).isNotNull();
        assertThat(rsa2).isNotNull();
    }

}