package cn.xbhel.techroad;

import cn.xbhel.techroad.config.props.FileDownloadProperties;
import cn.xbhel.techroad.config.props.FileUploadProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

/**
 * @author xbhel
 */
@SpringBootApplication
@EnableConfigurationProperties({FileDownloadProperties.class, FileUploadProperties.class})
public class FileApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FileApplication.class).run(args);
    }

    @Bean
    @ConditionalOnProperty(prefix = "xbhel.file.upload", name = "enabled", matchIfMissing = true)
    public MultipartResolver multipartResolver(
            FileUploadProperties props, ResourceLoader resourceLoader) throws IOException {
        var multipartResolver = new CommonsMultipartResolver();
        var map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(props.getEncoding())
                .whenHasText().to(multipartResolver::setDefaultEncoding);
        map.from(props.getMaxRequestSize())
                .to(e -> multipartResolver.setMaxUploadSizePerFile(e.toBytes()));
        map.from(props.getMaxFileSize())
                .to(e -> multipartResolver.setMaxUploadSize(e.toBytes()));
        map.from(props.getFileSizeThreshold())
                .asInt(size -> size.isNegative() ? 0 : size.toBytes())
                .to(multipartResolver::setMaxInMemorySize);
        // https://devhub.checkmarx.com/cve-details/CVE-2023-24998/
        var fileUpload = multipartResolver.getFileUpload();
        map.from(props.getFileCountSize()).to(fileUpload::setFileCountMax);
        if (StringUtils.hasText(props.getTempLocation())) {
            multipartResolver.setUploadTempDir(resourceLoader.getResource(props.getTempLocation()));
        }
        multipartResolver.setResolveLazily(props.isResolveLazily());
        return multipartResolver;
    }
}
