package com.wuyiccc.yuheng.service.impl;

import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.config.minio.YuhengMinioConfig;
import com.wuyiccc.yuheng.infrastructure.constants.StringConstants;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;
import com.wuyiccc.yuheng.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wuyiccc
 * @date 2023/9/3 06:53
 */
@Slf4j
@Service
public class MinioFileServiceImpl implements FileService {



    @Resource
    private YuhengMinioConfig yuhengMinioConfig;

    @Resource
    private MinioClient minioClient;


    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        log.info("contentType: {}", file.getContentType());

        String fileName = UUID.randomUUID().toString().replace(StringConstants.DASHED, StringConstants.EMPTY) + file.getOriginalFilename();
        PutObjectArgs build = PutObjectArgs.builder()
                .bucket(yuhengMinioConfig.getBucket())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        try {

            minioClient.putObject(build);
            return yuhengMinioConfig.getEndpoint() + StringConstants.SLASH + yuhengMinioConfig.getBucket() + StringConstants.SLASH + fileName;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new CustomException(YuhengBizCode.FILE_UPLOAD_FAILED);
        }
    }

}
