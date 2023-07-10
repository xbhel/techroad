package cn.xbhel.techroad.service;

import java.io.File;
import java.util.List;

/**
 * @author xbhel
 */
public interface FileStoreRepository {

    /**
     * 保存文件
     * @return 文件路径
     */
    String save();

    /**
     * 获取文件
     * @return 文件
     */
    File get();

    /**
     * 列出所有文件
     * @return 文件列表
     */
    List<String> list();

    /**
     * 移除文件
     * @return 移除成功返回 true 否则 false
     */
    boolean remove();
}
