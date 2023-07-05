package cn.xbhel.techroad.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
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
