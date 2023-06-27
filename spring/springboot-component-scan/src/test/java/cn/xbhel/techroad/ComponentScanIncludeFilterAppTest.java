package cn.xbhel.techroad;

import cn.xbhel.techroad.service.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ComponentScanApplication.class)
@ComponentScan(
        // 排除默认 @Component @Repository, @Service, @Controller 标记的组件
        useDefaultFilters = false,
        // 根据条件指定自定义过滤器来包含特定的组件
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Api.class)},
        // 指定包路名
        basePackageClasses = ComponentScanApplication.class
)
class ComponentScanIncludeFilterAppTest {

    @Autowired
    private ExampleService exampleService;

    @Test
    void shouldCreateExampleService() {
        assertThat(exampleService).isNotNull();
    }

}
