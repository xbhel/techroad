package cn.xbhel.techroad.commons.converter.impl;

import cn.xbhel.techroad.commons.converter.ByteArrayConverter;

import java.io.*;

/**
 * Object 和字节数组转换
 *
 * @author xbhel
 */
public class ObjectByteArrayConverter implements ByteArrayConverter<Object> {

    @Override
    public byte[] toByteArray(Object object) {
        try (
                var bos = new ByteArrayOutputStream();
                var oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot convert object to byte array!", e);
        }
    }

    @Override
    public Object fromByteArray(byte[] bytes) {
        try (
                var bis = new ByteArrayInputStream(bytes);
                var ois = new ObjectInputStream(bis)
        ) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot convert byte array to object!", e);
        }
    }
}
