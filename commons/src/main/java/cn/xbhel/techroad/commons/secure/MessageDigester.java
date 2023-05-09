package cn.xbhel.techroad.commons.secure;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法
 *
 * @author xbhel
 */
public class MessageDigester {

    private static final int DEFAULT_BUFFER_SIZE = 4096;
    /**
     * {@code MessageDigest} 为应用程序提供了一个消息摘要算法，例如 MD5、SHA-1、SHA-256，
     * 消息摘要是一个 one-way(单项) 的散列函数，它采用任意大小数据并输出一个固定长度的哈希值。
     */
    private final MessageDigest digest;

    public MessageDigester(MessageDigest digest) {
        this.digest = digest;
    }

    public byte[] digest(byte[] data) {
        var digester = getDigester();
        digester.update(data);
        return digester.digest();
    }

    public byte[] digest(File file) throws IOException {
        try (var in = new BufferedInputStream(new FileInputStream(file))) {
            return digest(in);
        }
    }

    public byte[] digest(InputStream in) throws IOException {
        var digester = getDigester();
        var bytes = new byte[DEFAULT_BUFFER_SIZE];
        while (in.read(bytes) != -1) {
            digester.update(bytes);
        }
        return digester.digest();
    }

    public static MessageDigester of(String algorithm) {
        try {
            return new MessageDigester(MessageDigest.getInstance(algorithm));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 使用之前重置消息摘要
     */
    private MessageDigest getDigester() {
        this.digest.reset();
        return this.digest;
    }

}
