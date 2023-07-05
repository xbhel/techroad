package cn.xbhel.techroad.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * {@link  MultipartProperties}
 * {@link CommonsMultipartResolver}
 *
 * @author xbhel
 */
@Data
@ConfigurationProperties(prefix = "spring.file-upload")
public class FileUploadProperties {
    /**
     * 是否开启文件上传
     */
    private boolean enable = true;

    /**
     * 指定文件解析时的编码
     */
    private String encoding = "utf-8";

    /**
     * <a href="https://devhub.checkmarx.com/cve-details/CVE-2023-24998/">commons-file-upload CVE-2023-24998</a>
     * 单次请求允许上传的文件数量
     */
    private Long fileCountSize;

    /**
     * 存储文件的位置，用于调用者写入文件内容至该目录
     */
    private String storeLocation;

    /**
     * web 容器接受到文件 (MultipartFile 对象) 存放的 临时/中间 目录，默认是 {code System.getProperty("java.io.tmpdir")}
     * 相对路径表示 {@code System.getProperty("java.io.tmpdir") + tempLocation}.
     * 等于 MultipartProperties#location
     */
    private String tempLocation;

    /**
     * 单个文件最多大小，默认为 10M
     */
    private DataSize maxFileSize = DataSize.ofMegabytes(1);

    /**
     * 单次请求允许上传的文件最大大小，默认为 10M
     */
    private DataSize maxRequestSize = DataSize.ofMegabytes(10);

    /**
     * 默认为 10KB
     * 文件写入磁盘的阈值，web 容器接受到文件 (MultipartFile 对象)时，超过该大小之后文件将写入磁盘，否则会先储存在内存中.
     * 避免小于零的阈值，因为它们会导致在 Commons FileUpload 中触发没有数据的字段的 NPE。
     */
    private DataSize fileSizeThreshold = DataSize.ofKilobytes(10);

    /**
     * 设置是否在文件或参数访问时延迟解析，设置为 true 在文件或参数被访问时才解析
     */
    private boolean resolveLazily = false;
}
