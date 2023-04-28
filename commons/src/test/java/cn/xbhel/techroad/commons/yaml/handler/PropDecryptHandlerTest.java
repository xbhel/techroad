package cn.xbhel.techroad.commons.yaml.handler;

import cn.xbhel.techroad.commons.yaml.PropHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author xbhel
 */
class PropDecryptHandlerTest {

    private static final PropHandler propDecryptHandler = new PropDecryptHandler();

    @Test
    void shouldSupportIfPropExpressionCorrect() {
        assertThat(propDecryptHandler.isSupport("ENC(passphrase)"))
                .isTrue();
    }

    @Test
    void shouldNotSupportIfPropExpressionError() {
        List.of("enc(passphrase)", "passphrase")
                .forEach(value -> assertThat(propDecryptHandler.isSupport(value)).isFalse());
    }
}