package cn.xbhel.techroad.config;

import cn.xbhel.techroad.commons.spring.UnitUtils;
import cn.xbhel.techroad.props.FileDownloadProperties;
import cn.xbhel.techroad.props.FileUploadProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.IOException;

import static cn.xbhel.techroad.commons.spring.UnitUtils.in;

/**
 * web 配置
 * @author xbhel
 */
@Configuration
@EnableConfigurationProperties({FileDownloadProperties.class, FileUploadProperties.class})
public class WebConfig implements WebMvcConfigurer  {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private FileUploadInterceptor fileUploadInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fileUploadInterceptor).addPathPatterns("/file/upload");
    }

    @Bean
    @ConditionalOnProperty(prefix = "xbhel.file.upload", name = "enabled", matchIfMissing = true)
    public MultipartResolver multipartResolver(FileUploadProperties props) throws IOException {
        var map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        var multipartResolver = new CommonsMultipartResolver();
        var fileUpload = multipartResolver.getFileUpload();
        // https://devhub.checkmarx.com/cve-details/CVE-2023-24998/
        multipartResolver.setResolveLazily(props.isResolveLazily());
        map.from(props.getFileCountSize()).to(fileUpload::setFileCountMax);
        map.from(props.getEncoding()).to(multipartResolver::setDefaultEncoding);
        map.from(props.getMaxFileSize()).to(in(multipartResolver::setMaxUploadSize));
        map.from(props.getMaxRequestSize()).to(in(multipartResolver::setMaxUploadSizePerFile));
        map.from(props.getFileSizeThreshold()).asInt(UnitUtils::getBytes).to(multipartResolver::setMaxInMemorySize);
        if (StringUtils.hasText(props.getTempLocation())) {
            multipartResolver.setUploadTempDir(applicationContext.getResource(props.getTempLocation()));
        }
        return multipartResolver;
    }

}
