package cn.xbhel.techroad.commons.yaml.handler;

import cn.xbhel.techroad.commons.util.ByteUtils;
import cn.xbhel.techroad.commons.yaml.PropHandler;

import java.util.regex.Pattern;

/**
 * 对加密属性 {@code ENC<passphrase>} 进行解密.
 *
 * @author xbhel
 */
public abstract class AbstractPropDecryptHandler implements PropHandler {

    private static final long serialVersionUID = 1L;
    private static final Pattern SYNTAX_EXPRESSION_RULE = Pattern.compile("^(ENC\\().+(\\))$");

    protected abstract byte[] decrypt(String encryptValue);

    @Override
    public String handle(String propValue) {
        var matcher = SYNTAX_EXPRESSION_RULE.matcher(propValue);
        if(!matcher.matches()) {
            return propValue;
        }
        // 获取第一个组结尾 ~ 第二个组开头的内容 => group1 = 'ENC(', group2 = ')'
        var encryptValue = propValue.substring(matcher.end(1), matcher.start(2));
        return ByteUtils.toString(decrypt(encryptValue));
    }

    @Override
    public boolean isSupport(String propValue) {
        return SYNTAX_EXPRESSION_RULE.matcher(propValue).matches();
    }

}
