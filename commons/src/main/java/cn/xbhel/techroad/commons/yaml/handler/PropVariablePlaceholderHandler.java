package cn.xbhel.techroad.commons.yaml.handler;

import cn.xbhel.techroad.commons.yaml.PropHandler;

/**
 * 解析变量引用 {@code ${variable_name}}，包括上文变量、系统变量、环境变量引用，替换为真实的值.
 * 如果变量引用表达式中使用了默认值表达式，如果变量不存在则回退为默认值。
 * @author xbhel
 */
public class PropVariablePlaceholderHandler implements PropHandler {

    @Override
    public String handle(String propValue) {
        return null;
    }

    @Override
    public boolean isSupport(String propValue) {
        return false;
    }

}
