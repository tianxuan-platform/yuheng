package com.wuyiccc.yuheng.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wuyiccc
 * @date 2023/9/3 06:53
 */
public interface FileService {


    /**
     * 文件上传
     * @param file 文件数据
     * @return 文件访问url地址
     */
    String uploadFile(MultipartFile file) throws IOException;
}
