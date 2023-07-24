package cn.xbhel.techroad.commons.io;

/**
 * 保存时对于已存在的文件的文件覆盖策略.
 * @author xbhel
 */
public enum FileOverride {
    /** 忽略本次写入 */
    IGNORE,
    /** 抛出异常 */
    EXCEPTION,
    /** 覆盖已有文件 */
    OVERRIDE,
    /** 生成一个新文件  */
    RENEW;
}
