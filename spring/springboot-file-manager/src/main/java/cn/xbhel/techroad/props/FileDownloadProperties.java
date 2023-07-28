package cn.xbhel.techroad.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 文件下载属性
 * @author xbhel
 */
@Data
@ConfigurationProperties("xbhel.file.download")
public class FileDownloadProperties {

    /**
     * 下载目录
     */
    private String location;

    /**
     * 允许上传的文件类型匹配模式，接受一个正则表达式.
     */
    private String fileTypePattern;

    /**
     * 下载文件名称匹配模式，接受一个正则表达式，避免名称中包含非法字符.
     * 默认名称由 [字母、数字、'-_.'] 组成
     */
    private String filenamePattern = "^[A-Za-z0-9\\-_.]+$";

    /**
     * 下载路径白名单，为空表示任何路径
     */
    private List<String> allowPaths;
}
