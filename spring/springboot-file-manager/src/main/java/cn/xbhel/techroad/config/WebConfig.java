package cn.xbhel.techroad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * web 配置
 * @author xbhel
 */
@Configuration
public class WebConfig implements WebMvcConfigurer  {

    @Resource
    private FileUploadInterceptor fileUploadInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fileUploadInterceptor).addPathPatterns("/file/upload");
    }

}
