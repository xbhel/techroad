package cn.xbhel.techroad;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xbhel
 */
@SpringBootApplication
public class FileApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FileApplication.class).run(args);
    }
}