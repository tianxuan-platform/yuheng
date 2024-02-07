package com.wuyiccc.yuheng.controller;

import com.wuyiccc.yuheng.infrastructure.pojo.R;
import com.wuyiccc.yuheng.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author wuyiccc
 * @date 2024/2/7 15:32
 */
@Slf4j
@Validated
@RequestMapping("/file")
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/uploadFile")
    public R<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadFile(file);
        return R.data(url);
    }
}
