package com.wuyiccc.yuheng.infrastructure.config.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyiccc
 * @date 2024/2/7 15:33
 * minio配置
 */
@ConfigurationProperties(prefix = "yuheng.minio")
@Configuration
@Data
public class YuhengMinioConfig {

    private String endpoint;

    private String bucket;

    private String accessKey;

    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
