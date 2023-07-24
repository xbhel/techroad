package cn.xbhel.techroad.controller;

import cn.xbhel.techroad.commons.io.DiskFileRepository;
import cn.xbhel.techroad.commons.io.FileRepository;
import cn.xbhel.techroad.config.props.FileUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xbhel
 */
@CrossOrigin
@RestController
@RequestMapping(("/file"))
public class FileController {

    private final FileRepository fileRepository;

    @Autowired
    public FileController(FileUploadProperties props) {
        this.fileRepository = new DiskFileRepository(Path.of(props.getLocation()), true);
    }

    @PostMapping("/upload")
    public List<String> upload(@RequestPart("file") MultipartFile[] files) throws IOException {
        List<String> paths = new ArrayList<>(files.length);
        for (MultipartFile f : files) {
            try(InputStream in = f.getInputStream()) {
                Path path = fileRepository.save(in, Paths.get(Objects.requireNonNull(f.getOriginalFilename())));
                paths.add(path.toString());
            }
        }
        return paths;
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
        return ResponseEntity.ok(true);
    }

}
