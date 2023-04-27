package cn.xbhel.techroad.commons.yaml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

/**
 * 用于对 yaml、json 的数据格式进行解析.
 * <p>
 * 因为 yaml、json 格式非常适合用作配置声明，针对这一点，我们在解析过程中拓展以下功能：
 * 1.自动解密：使用用户提供的算法对加密属性进行自动解密。通常配置文件中会包含一些密码相关的敏感信息，
 * 并对这类信息进行加密，然后在应用中使用相应的算法进行解密，我们可以使用 `ENC(passphrase)` 去声明加密属性.
 * 2.变量替换：使用系统变量或者环境变量去替换变量的值。在配置文件中，有时我们为了避免敏感信息泄露（如私钥），
 * 我们会将其配置在环境变量或者系统变量中，然后再配置文件中使用占位符 `${variable_name}`代替，然后在运行
 * 时在获取变量的真实值去代替.
 * 3.默认值：当在使用变量时，如果变量不存在，希望能够回退到指定的默认值.
 * 4.类转换：配置声明中可能包含多中配置信息(数据库、中间件)等，对于这些信息我们可能用需要将其组织到相应的配置类中，
 * 然后再进行应用.
 *
 * @author xbhel
 */
public class YamlEnhancer {

    /** 默认的 ObjectMapper */
    private static final ObjectMapper DEFAULT_YAML_MAPPER = defaultObjectMapper();

    /** ObjectMapper 用于解析 Yaml */
    protected final ObjectMapper yamlMapper;

    public YamlEnhancer() {
        this(DEFAULT_YAML_MAPPER);
    }

    public YamlEnhancer(ObjectMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
    }

    private static ObjectMapper defaultObjectMapper() {
        return YAMLMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(JsonParser.Feature.ALLOW_YAML_COMMENTS)
                .propertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE)
                .nodeFactory(new PropJsonNodeFactory())
                .build();
    }
}
