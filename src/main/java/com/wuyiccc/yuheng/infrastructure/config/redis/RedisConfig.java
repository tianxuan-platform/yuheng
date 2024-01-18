package com.wuyiccc.yuheng.infrastructure.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/18 23:19
 */
@Slf4j
@EnableConfigurationProperties(YuhengRedissonConfig.class)
@Configuration
public class RedisConfig {

    @Resource
    private YuhengRedissonConfig yuhengRedissonConfig;

    @Resource
    private ObjectMapper objectMapper;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedissonAutoConfigurationCustomizer redissonCustomizer() {
        return config -> {
            config.useSingleServer().setAddress("redis://" + host + ":" + port);
            config.useSingleServer().setPassword(password);
            config.setThreads(yuhengRedissonConfig.getThreads())
                    .setNettyThreads(yuhengRedissonConfig.getNettyThreads())
                    .setCodec(new JsonJacksonCodec(objectMapper));
            YuhengRedissonConfig.SingleServerConfig singleServerConfig = yuhengRedissonConfig.getSingleServerConfig();
            if (Objects.nonNull(singleServerConfig)) {
                // 使用单机模式
                config.useSingleServer()
                        //设置redis key前缀
                        .setNameMapper(new RedisKeyPrefixHandler(yuhengRedissonConfig.getKeyPrefix()))
                        .setTimeout(singleServerConfig.getTimeout())
                        .setClientName(singleServerConfig.getClientName())
                        .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                        .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                        .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                        .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
                        .setDatabase(database);
            }
            log.info("初始化 redis 配置");
        };
    }
}
