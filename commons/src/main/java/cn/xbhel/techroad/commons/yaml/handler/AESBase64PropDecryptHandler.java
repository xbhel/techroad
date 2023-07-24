package cn.xbhel.techroad.commons.yaml.handler;

import cn.xbhel.techroad.commons.secure.KeyUtils;
import cn.xbhel.techroad.commons.secure.symmetric.AES;
import cn.xbhel.techroad.commons.secure.symmetric.SymmetricAlgorithm;
import cn.xbhel.techroad.commons.util.Base64Utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * 使用 AES_GCM_NOPADDING 算法对 Base64 格式的属性进行解密.
 * @author xbhel
 */
public class AESBase64PropDecryptHandler extends AbstractPropDecryptHandler {

    private static final long serialVersionUID = 1L;
    private final AES aes;

    public AESBase64PropDecryptHandler(byte[] key) {
       this.aes = getAES(key);
    }
    @Override
    protected byte[] decrypt(String encryptValue) {
        return aes.decrypt(Base64Utils.decode(encryptValue));
    }

    private static AES getAES(byte[] key) {
        SecretKey secretKey = KeyUtils.getKey(SymmetricAlgorithm.AES.getName(), key);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, new byte[12]);
        AES aesGmc = new AES(SymmetricAlgorithm.AES_GCM_NOPADDING, secretKey);
        aesGmc.setAlgorithmParameterSpec(gcmParameterSpec);
        return aesGmc;
    }
}
