package cn.xbhel.techroad.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取 ApplicationContext 对象并以静态方式对外提供
 * @author xbhel
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext appContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext; // NOSONAR
    }

    public static ApplicationContext get() {
        return appContext;
    }
}
