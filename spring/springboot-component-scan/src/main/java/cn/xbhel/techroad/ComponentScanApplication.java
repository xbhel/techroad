package cn.xbhel.techroad;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xbhel
 */
@DescriptionScan
@SpringBootApplication
public class ComponentScanApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ComponentScanApplication.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }

}
