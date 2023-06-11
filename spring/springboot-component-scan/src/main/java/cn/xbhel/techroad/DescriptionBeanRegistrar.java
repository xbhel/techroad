package cn.xbhel.techroad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 发现标记了 {@link Description} 注解的 bean，并对其进行增强.
 *
 * @author xbhel
 */
@Slf4j
@ComponentScan
public class DescriptionBeanRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 DescriptionScan 注解的属性
        var annoAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(DescriptionScan.class.getName()));
        Objects.requireNonNull(annoAttrs);

        // 获取 DescriptionScan 扫描的包
        var packages = Arrays.stream(annoAttrs.getStringArray("basePackages"))
                .filter(StringUtils::hasText)
                .toArray(String[]::new);

        // 创建扫描器，扫描包下所有的标记了 @Description 的 bean
        var descriptionBeanScanner = new DescriptionBeanScanner(registry);
        descriptionBeanScanner.setResourceLoader(this.resourceLoader);
        descriptionBeanScanner.addIncludeFilter(new AnnotationTypeFilter(Description.class));

        // 拿到所有标记了 @Description 的 bean 声明，对 bean 进行增强
        descriptionBeanScanner.doScan(packages)
                .stream()
                .map(BeanDefinitionHolder::getBeanDefinition)
                .forEach(beanDefinition -> {
                    var definition = (GenericBeanDefinition) beanDefinition;
                    log.info("bean definition {}", definition);
                });
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
