package com.sbsj.dreamwing.test;

import com.sbsj.dreamwing.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class S3Test {
    private final S3Uploader s3Uploader;

    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return s3Uploader.uploadFile(file);
    }
}
