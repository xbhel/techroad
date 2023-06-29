package cn.xbhel.techroad;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FileUploadDownloadApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FileUploadDownloadApplication.class).run(args);
    }

}
