package cn.xbhel.techroad;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xbhel
 */
@SpringBootApplication
public class AnnotationScanApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AnnotationScanApplication.class)
                .build()
                .run(args);
    }

}
