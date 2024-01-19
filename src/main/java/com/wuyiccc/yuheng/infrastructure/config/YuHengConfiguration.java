package com.wuyiccc.yuheng.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyiccc
 * @date 2024/1/19 23:58
 */
@Slf4j
@MapperScan("com.wuyiccc.yuheng.mapper")
@ComponentScan("com.wuyiccc.yuheng")
@Configuration
public class YuHengConfiguration {

    public YuHengConfiguration() {
        log.info("YuHeng Config....");
    }
}
