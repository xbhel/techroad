package cn.xbhel.techroad.commons.io;

import java.io.File;
import java.util.function.Predicate;

@FunctionalInterface
public interface FileValidator extends Predicate<File> {
}
