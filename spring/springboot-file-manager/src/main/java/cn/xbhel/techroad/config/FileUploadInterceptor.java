package cn.xbhel.techroad.config;

import cn.xbhel.techroad.props.FileUploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件验证拦截器
 * @author xbhel
 */
@Component
@RequiredArgsConstructor
public class FileUploadInterceptor implements HandlerInterceptor, BeanFactoryAware {

    private final FileUploadProperties props;
    private BeanFactory beanFactory;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        final MultipartResolver resolver = this.beanFactory.getBean(MultipartResolver.class);

        // 非文件上传请求则放行
        if(!resolver.isMultipart(request)) return true;

        var multipartHttpServletRequest = resolver.resolveMultipart(request);
        //new ServletRequestContext(request)
        return true;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
