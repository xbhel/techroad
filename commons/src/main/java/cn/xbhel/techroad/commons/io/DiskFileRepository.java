package cn.xbhel.techroad.commons.io;

import cn.hutool.core.io.file.FileNameUtil;
import cn.xbhel.techroad.commons.util.AssertUtils;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * 磁盘文件存储.
 *
 * @author xbhel
 */
@Data
public class DiskFileRepository implements FileRepository {

    private static final int MAX_RANDOM = Short.MAX_VALUE;
    private static final SecureRandom random = new SecureRandom(SecureRandom.getSeed(16));

    /**
     * 基础路径
     */
    private Path basePath = Paths.get("/");
    /**
     * 如果路径不存在是否创建
     */
    private boolean createIfDirNotExist = false;
    /**
     * 如果文件存在文件的覆盖策略
     */
    private FileOverride fileOverride = FileOverride.EXCEPTION;
    /**
     * 文件名称编码器
     */
    private UnaryOperator<String> fileNameEncoder;


    public DiskFileRepository() {
    }

    public DiskFileRepository(Path basePath) {
        this.basePath = basePath;
    }

    public DiskFileRepository(Path basePath, boolean createIfDirNotExist) {
        this.basePath = basePath;
        this.createIfDirNotExist = createIfDirNotExist;
    }

    @Override
    public File get(Path path) {
        return null;
    }

    @Override
    public List<String> list() {
        return Collections.emptyList();
    }

    @Override
    public List<String> list(Path dir) {
        return Collections.emptyList();
    }

    @Override
    public boolean remove() {
        return false;
    }

    @Override
    public Path save(InputStream in, Path target) throws IOException {
        AssertUtils.notNull("both parameters 'in' and 'target' must not be null", in, target);
        List<CopyOption> copyOptions = new ArrayList<>();
        String fileName = target.getFileName().toString();
        Path filePath = this.basePath.resolve(getEncodeFileName(fileName));
        Path fileDir = filePath.getParent();
        // 文件存在时的覆盖策略
        if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
            if (this.fileOverride == FileOverride.IGNORE) {
                return filePath;
            }
            if (this.fileOverride == FileOverride.EXCEPTION) {
                throw new FileAlreadyExistsException("the file '" + filePath + "' already exists.");
            }
            if (this.fileOverride == FileOverride.OVERRIDE) {
                copyOptions.add(StandardCopyOption.REPLACE_EXISTING);
            } else {
                // RENEW 生成的新文件必须循环判断是否依旧存在
                do {
                    fileName = FileNameUtil.mainName(fileName) + random.nextInt(MAX_RANDOM)
                            + FileNameUtil.extName(fileName);
                    filePath = fileDir.resolve(fileName);
                } while (Files.exists(filePath) && !Files.isDirectory(filePath));
            }
        }
        // 多级路径下，创建所有不存在的路径
        if (Files.notExists(fileDir) && this.createIfDirNotExist) {
            Files.createDirectories(fileDir);
        }
        Files.copy(in, filePath, copyOptions.toArray(new CopyOption[]{}));
        return filePath;
    }

    private String getEncodeFileName(String fileName) {
        return this.fileNameEncoder == null ? fileName : this.fileNameEncoder.apply(fileName);
    }

}
