package com.wuyiccc.yuheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuyiccc
 * @date 2023/9/12 22:18
 */
@SpringBootApplication
@MapperScan("com.wuyiccc.yuheng.mapper")
public class YuhengApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuhengApplication.class, args);
    }
}
