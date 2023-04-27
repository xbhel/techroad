package cn.xbhel.techroad.commons.yaml;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 继承 {@link JsonNodeFactory} 并重写 {@code textNode()}，然后应用一系列 {@link PropHandler} 对属性进行处理.
 * 按顺序调用 PropHandler 进行处理，依次将前一个处理后的值传入下一个 PropHandler 进行处理.
 *
 * @author xbhel
 */
public class PropJsonNodeFactory extends JsonNodeFactory {

    private static final long serialVersionUID = 1L;

    private List<PropHandler> propHandlers = new ArrayList<>();

    public PropJsonNodeFactory setPropHandlers(List<PropHandler> propHandlers) {
        this.propHandlers = propHandlers;
        return this;
    }

    public PropJsonNodeFactory addPropHandler(PropHandler propHandler) {
        this.propHandlers.add(propHandler);
        return this;
    }

    @Override
    public TextNode textNode(String text) {
        String propValue = text;
        // 按顺序调用 PropHandle 进行处理，依次将前一个处理后的值传入下一个 PropHandle 进行处理
        if (propHandlers != null && propHandlers.size() > 0) {
            for (PropHandler propHandler : propHandlers) {
                if (propHandler.isSupport(propValue)) {
                    propValue = propHandler.handle(propValue);
                }
            }
        }
        return super.textNode(propValue);
    }
}
