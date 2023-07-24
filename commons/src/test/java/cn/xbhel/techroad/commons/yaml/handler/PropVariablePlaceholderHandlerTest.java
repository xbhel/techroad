package cn.xbhel.techroad.commons.yaml.handler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropVariablePlaceholderHandlerTest {

    private final PropVariablePlaceholderHandler handler = new PropVariablePlaceholderHandler();

    @Test
    void shouldGetValueFromSystemVariable() {
        System.setProperty("USER", "tom");
        assertTrue(handler.isSupport("${USER}"));
        assertEquals("tom", handler.handle("${USER}"));
    }

    @Test
    void shouldGetValueFromEnvVariable() {
        try (var h = Mockito.mockStatic(PropVariablePlaceholderHandler.class)){
            h.when(() -> PropVariablePlaceholderHandler.getEnvVariable("USER"))
                    .thenReturn("tom");
            assertTrue(handler.isSupport("${USER}"));
            assertEquals("tom", handler.handle("${USER}"));
        }
    }

}