package cn.xbhel.techroad.commons.secure;

import cn.xbhel.techroad.commons.util.ByteUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class KeyUtilsTest {

    @Test
    void generateAESKey() throws NoSuchAlgorithmException {
        byte[] newKey = ByteUtils.toBytes("hello world!");
        MessageDigest sha = MessageDigest.getInstance("SHA-512");
        newKey = sha.digest(newKey);
        newKey = Arrays.copyOf(newKey, 16);
        // 16 byte = 16 * 8 = 128 位
        // 刚好满足 AES key 的长度
        SecretKeySpec keySpec =  new SecretKeySpec(newKey, "AES");

        assertThat(keySpec).isNotNull();
    }

}