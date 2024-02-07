# 玉衡服务端基础业务框架

> 一个只需要引入一个依赖便可快速开发业务的框架

![logo.png](doc%2Fimage%2Flogo.png)


# 1. 使用教程

## 1.1 创建一个普通的java maven工程

## 1.2 配置pom依赖

1. pom依赖管理
```xml
    <dependencyManagement>

        <dependencies>
            <dependency>
                <groupId>com.wuyiccc</groupId>
                <artifactId>yuheng</artifactId>
                <version>beta-0.2</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

2. pom依赖

```xml
    <dependencies>

        <dependency>
            <groupId>com.wuyiccc</groupId>
            <artifactId>yuheng</artifactId>
            <version>beta-0.2</version>
        </dependency>

    </dependencies>
```
## 1.3 配置application.yml

```yml
server:
  port: 9000
  tomcat:
    uri-encoding: UTF-8
    max-swallow-size: -1

spring:
  application:
    name: vega
  main:
    allow-circular-references: true  #允许循环引用
    allow-bean-definition-overriding: true
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://mysql.local.wuyiccc.com:12011/vega?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    password: 123
    hikari:
      connection-timeout: 30000               # 等待连接池分配连接的最大时间（毫秒），超过这个时长还没有可用的连接，则会抛出SQLException
      minimum-idle: 5                         # 最小连接数
      maximum-pool-size: 20                   # 最大连接数
      auto-commit: true                       # 自动提交
      idle-timeout: 600000                    # 连接超时的最大时长（毫秒），超时则会被释放（retired）
      pool-name: DataSourceHikariCP           # 连接池的名字
      max-lifetime: 18000000                  # 连接池的最大生命时长（毫秒），超时则会被释放（retired）
      connection-test-query: SELECT 1
  redis:
    host: redis.local.wuyiccc.com
    port: 12021
    password: 123
    database: 1
    ssl: false



mybatis-plus:
  mapper-locations: classpath*:/mapper/*.xml
  global-config:
    db-config:
      # 雪花算法id
      id-type: assign_id
      logic-delete-field: delFlag
      logic-delete-value: 1
      logic-not-delete-value: 0
    # 去掉mybatis-plus banner图片
    banner: false
  # sql日志打印
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl


# 配置日志级别
logging:
  level:
    root: info
  config: classpath:logback.xml



####################
# yuheng配置
####################
# 安全放行路径
yuheng:
  security:
    # 忽略路径
    ignore:
      urls:
        - /user/login
        - /heartbeat
        - /error
    jwt:
      key: yuheng_tianxuan_platform_jwt_key123
  # redisson 配置
  redisson:
    # redis key前缀
    keyPrefix:
    # 线程池数量
    threads: 4
    # Netty线程池数量
    nettyThreads: 8
    # 单节点配置
    singleServerConfig:
      # 客户端名称
      clientName: ${spring.application.name}
      # 最小空闲连接数
      connectionMinimumIdleSize: 8
      # 连接池大小
      connectionPoolSize: 32
      # 连接空闲超时，单位：毫秒
      idleConnectionTimeout: 10000
      # 命令等待超时，单位：毫秒
      timeout: 3000
      # 发布和订阅连接池大小
      subscriptionConnectionPoolSize: 50
  minio:
    endpoint: http://minio.local.wuyiccc.com:12031
    bucket: vega
    access-key: admin
    secret-key: 12345678
####################
```

## 1.4 配置logback.yml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>

    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
    <conversionRule conversionWord="wex"
                    converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
    <conversionRule conversionWord="wEx"
                    converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN"
              value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <!-- 控制台输出设置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>

    <!-- 按照每天生成日志文件 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志文件输出的文件名 -->
            <fileNamePattern>./logs/%d{yyyyMMdd}/vega.log</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>

    <!--debug(打印最多) info warn error(只打印error)-->
    <root level="info">
        <appender-ref ref="FILE"/>
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
```

## 1.5 创建SpringBoot启动类
```java
package com.wuyiccc.vega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuyiccc
 * @date 2024/1/19 23:54
 */
@SpringBootApplication
public class VegaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VegaApplication.class, args);
    }
}
```

## 1.6 创建数据库
[yuheng.sql](doc%2Fsql%2Fyuheng.sql)


# 2. api文档地址

[User.http](doc%2Fapi%2FUser.http)

[Index.http](doc%2Fapi%2FIndex.http)

# 3. sql脚本地址
[yuheng.sql](doc%2Fsql%2Fyuheng.sql)

# 4. 使用案例
[vega-nginx服务管理平台](https://github.com/tianxuan-platform/vega)
