package cn.xbhel.techroad;

import cn.xbhel.techroad.controller.FileManagerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理器
 * @author xbhel
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {FileManagerController.class}, annotations = RestController.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<String> fileUploadException(MaxUploadSizeExceededException ex) {
        log.error("An exception occur in the file upload, {}. stack trace:", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
