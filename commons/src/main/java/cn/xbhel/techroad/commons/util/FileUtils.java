package cn.xbhel.techroad.commons.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * IO 操作
 * @author xbhel
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static byte[] readAllBytes(Path path) {
        try (var in = Files.newInputStream(path)) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
