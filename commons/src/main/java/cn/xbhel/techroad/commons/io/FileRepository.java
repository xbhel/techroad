package cn.xbhel.techroad.commons.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 */
public interface FileRepository {

    /**
     * 获取文件
     * @param path 文件路径
     * @return 文件.
     */
    File get(Path path);

    /**
     * 列出所有文件
     * @return 所有文件的路径.
     */
    List<String> list();

    /**
     * 列出指定目录下的文件
     * @param dir 目录
     * @return 指定目录下所有文件的路径.
     */
    List<String> list(Path dir);

    /**
     * 删除文件
     * @return 删除成功返回 true 否则 false.
     */
    boolean remove();

    /**
     * 从流中获取数据并保存至指定路径
     *
     * @param in     输入流
     * @param target 数据保存的路径
     * @return 数据保存的路径，如果对路径进行了<b>混淆或编码</b>，那么返回的路径和 {@code target} 可能不一样.
     */
    Path save(InputStream in, Path target) throws IOException;

    /**
     * 保存文件
     *
     * @param source 路径指向源文件
     * @param target 文件保存的路径
     * @return 文件保存的路径，如果对路径进行了<b>混淆或编码</b>，那么返回的路径和 {@code target} 可能不一样.
     */
    default Path save(Path source, Path target) throws IOException {
        return save(Files.newInputStream(source), target);
    }

    /**
     * 保存文件
     *
     * @param source 源文件
     * @param target 文件保存的路径
     * @return 文件保存的路径，如果对路径进行了<b>混淆或编码</b>，那么返回的路径和 {@code target} 可能不一样.
     */
    default Path save(File source, Path target) throws IOException {
        return save(new BufferedInputStream(new FileInputStream(source)), target);
    }

}
