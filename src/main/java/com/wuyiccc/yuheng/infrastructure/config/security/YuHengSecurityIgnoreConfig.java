package com.wuyiccc.yuheng.infrastructure.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/7/17 13:13
 * <br>
 * 不需要权限拦截的url
 */
@ConfigurationProperties(prefix = "yuheng.security.ignore")
@Configuration
@Data
public class YuHengSecurityIgnoreConfig {

    private List<String> urls = new ArrayList<>();

}
