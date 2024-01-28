package com.wuyiccc.yuheng.infrastructure.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyiccc
 * @date 2024/1/28 11:19
 */
@ConfigurationProperties(prefix = "yuheng.security.jwt")
@Configuration
@Data
public class YuHengSecurityJwtConfig {

    private String key;
}
