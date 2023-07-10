package cn.xbhel.techroad.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

/**
 * @author xbhel
 */
@Configuration
@EnableConfigurationProperties({FileUploadProperties.class})
@ConditionalOnProperty(prefix = "spring.file-upload", name = "enabled", matchIfMissing = true)
public class FileUploadConfig {

    @Bean
    public MultipartResolver multipartResolver(
            FileUploadProperties properties, ResourceLoader resourceLoader) throws IOException {
        var multipartResolver = new CommonsMultipartResolver();
        var map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties.getEncoding()).whenHasText().to(multipartResolver::setDefaultEncoding);
        map.from(properties.getMaxRequestSize()).to(e -> multipartResolver.setMaxUploadSizePerFile(e.toBytes()));
        map.from(properties.getMaxFileSize()).to(e -> multipartResolver.setMaxUploadSize(e.toBytes()));
        map.from(properties.getFileSizeThreshold()).as(size -> (int) (size.toBytes() > 0 ? size.toBytes() : 0))
                .to(multipartResolver::setMaxInMemorySize);
        // https://devhub.checkmarx.com/cve-details/CVE-2023-24998/
        map.from(properties.getFileCountSize()).to(e -> multipartResolver.getFileUpload().setFileCountMax(e));
        if (StringUtils.hasText(properties.getTempLocation())) {
            multipartResolver.setUploadTempDir(resourceLoader.getResource(properties.getTempLocation()));
        }
        multipartResolver.setResolveLazily(properties.isResolveLazily());
        return multipartResolver;
    }

}
