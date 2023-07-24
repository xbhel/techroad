package cn.xbhel.techroad;

import cn.xbhel.techroad.comps.ModelA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FilterBeanApp.class)
class FilterBeanAppTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("标记了@Model应该被创建为Bean")
    void shouldCreatedMarkedModelAnno() {
        assertThat(context.getBean(ModelA.class)).isNotNull();
    }
}
