package cn.xbhel.techroad;

import java.lang.annotation.*;
import java.util.function.Supplier;

/**
 * 所有标记 @Description 注解的类都会作为候选 Bean 由 Spring 创建，与 @Component、@Service 等注解作用一致.
 * 换一句话说标记了 @Description 注解的类都要能被实例化从而成为 Spring Bean，对于接口或抽象类这种不能实例化的，
 * 可以通过 {@link org.springframework.beans.factory.support.GenericBeanDefinition#setInstanceSupplier(Supplier)}
 * 去重写定义 Bean 的实例化，如通过动态代理生成代理类。
 * @author xbhel
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Description {
    /** 描述信息 */
    String value() default "";
}
