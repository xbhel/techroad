package cn.xbhel.techroad;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DescriptionBeanRegistrar.class})
public @interface DescriptionScan {
    /**
     * 要扫描的包
     */
    String[] basePackages() default {"cn.xbhel.techroad"};
}
