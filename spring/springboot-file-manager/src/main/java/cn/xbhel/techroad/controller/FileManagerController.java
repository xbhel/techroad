package cn.xbhel.techroad.controller;

import cn.xbhel.techroad.commons.httpassist.ResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(("/file"))
public class FileStoreController {

    @PostMapping("/upload")
    public ResultVo<Object> upload(@RequestParam("file") MultipartFile file) {
        return ResultVo.success(null);
    }

    @GetMapping("/download")
    public void download(@RequestParam("id") String id, HttpServletResponse response) {
        // nothing
    }

    @GetMapping("/list")
    public void list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        // nothing
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("id") String id) {
        // #fileService.delete(id);
        return ResponseEntity.ok(true);
    }

}
