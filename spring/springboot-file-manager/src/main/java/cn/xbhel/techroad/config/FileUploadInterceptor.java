package cn.xbhel.techroad.config;

import cn.xbhel.techroad.config.props.FileUploadProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件验证拦截器
 * @author xbhel
 */
@Component
@RequiredArgsConstructor
@DependsOn("multipartResolver")
public class FileUploadInterceptor implements HandlerInterceptor {

    private final FileUploadProperties props;
    private final CommonsMultipartResolver resolver;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 非文件上传请求则放行
        if(!resolver.isMultipart(request)) return true;
        var multipartHttpServletRequest = resolver.resolveMultipart(request);
        //new ServletRequestContext(request)
        return true;
    }
}
