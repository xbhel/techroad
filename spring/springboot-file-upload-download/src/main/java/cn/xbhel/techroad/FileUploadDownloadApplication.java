package cn.xbhel.techroad;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableConfigurationProperties({MultipartProperties.class})
public class FileUploadDownloadApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FileUploadDownloadApplication.class).run(args);
    }
    @Bean
    public MultipartResolver multipartResolver(MultipartProperties properties,
                                               ResourceLoader resourceLoader) throws IOException {
        var multipartResolver = new CommonsMultipartResolver();
        var map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        // 设置是否在文件或参数访问时延迟解析，设置为 true 在文件或参数访问时才解析
        multipartResolver.setResolveLazily(true);
        multipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
        map.from(properties.getMaxRequestSize()).to(e -> multipartResolver.setMaxUploadSize(e.toBytes()));
        map.from(properties.getMaxFileSize()).to(e -> multipartResolver.setMaxUploadSizePerFile(e.toBytes()));
        map.from(properties.getFileSizeThreshold()).to(e -> multipartResolver.setMaxInMemorySize((int)e.toBytes()));
        if(StringUtils.hasText(properties.getLocation())) {
            multipartResolver.setUploadTempDir(resourceLoader.getResource(properties.getLocation()));
        }
        return multipartResolver;
    }
}
