package cn.xbhel.techroad.commons.yaml.handler;

import cn.xbhel.techroad.commons.yaml.PropHandler;

import java.util.regex.Pattern;

/**
 * 对加密属性 {@code ENC<passphrase>} 进行解密.
 *
 * @author xbhel
 */
public class PropDecryptHandler implements PropHandler {

    private static final long serialVersionUID = 1L;

    private static final Pattern SYNTAX_EXPRESSION_RULE = Pattern.compile("^(ENC\\().+(\\))$");

    @Override
    public String handle(String propValue) {
        return null;
    }

    @Override
    public boolean isSupport(String propValue) {
        return SYNTAX_EXPRESSION_RULE.matcher(propValue).matches();
    }

}
