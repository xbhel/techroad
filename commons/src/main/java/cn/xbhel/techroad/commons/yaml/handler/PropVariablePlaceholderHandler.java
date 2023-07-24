package cn.xbhel.techroad.commons.yaml.handler;

import cn.hutool.core.text.CharPool;
import cn.xbhel.techroad.commons.yaml.PropHandler;
import com.google.common.annotations.VisibleForTesting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析变量引用，语法 {@code ${variableName:defaultValue}}，包括上文变量、系统变量、环境变量引用，替换为真实的值.
 * 如果变量引用表达式中使用了默认值表达式，如果变量不存在则回退为默认值。
 *
 * @author xbhel
 */
public class PropVariablePlaceholderHandler implements PropHandler {

    private static final long serialVersionUID = 1L;
    private static final Pattern SYNTAX_EXPRESSION_RULE = Pattern.compile("^(\\$\\{).+(})$");

    @Override
    public String handle(String propValue) {
        Matcher matcher = SYNTAX_EXPRESSION_RULE.matcher(propValue);
        // 必须调用 matches() 才触发正则匹配计算，否则后续 matcher.end 是获取不到的
        if (!matcher.matches()) {
            return propValue;
        }
        String expression = propValue.substring(matcher.end(1), matcher.start(2));
        String defaultValue = null;
        String variableName = expression;
        // 是否有默认值
        int index = expression.lastIndexOf(CharPool.COLON);
        if (index > 0) {
            variableName = expression.substring(0, index);
            // 如果‘:’是最后一个 ${variableName:}，则会返回空串
            defaultValue = expression.substring(index + 1);
        }
        // 系统变量中获取
        propValue = System.getProperty(variableName);
        // 环境变量中获取
        if (propValue == null) {
            propValue = getEnvVariable(variableName);
        }
        // 使用默认值
        if (propValue == null) {
            propValue = defaultValue;
        }
        return propValue;
    }

    @Override
    public boolean isSupport(String propValue) {
        return SYNTAX_EXPRESSION_RULE.matcher(propValue).matches();
    }

    @VisibleForTesting
    static String getEnvVariable(String variableName) {
        return System.getenv(variableName);
    }

}
