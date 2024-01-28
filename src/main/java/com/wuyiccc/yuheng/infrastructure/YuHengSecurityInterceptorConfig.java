package com.wuyiccc.yuheng.infrastructure;

import com.wuyiccc.yuheng.infrastructure.config.security.YuHengSecurityIgnoreConfig;
import com.wuyiccc.yuheng.infrastructure.interceptor.YuHengSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:02
 * <br>
 * 拦截器配置
 */
@Configuration
public class YuHengSecurityInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private YuHengSecurityIgnoreConfig yuHengSecurityIgnoreConfig;

    @Bean
    public YuHengSecurityInterceptor yuHengSecurityContextInterceptor() {
        return new YuHengSecurityInterceptor(yuHengSecurityIgnoreConfig);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册用户信息获取拦截器
        registry.addInterceptor(yuHengSecurityContextInterceptor());
    }

}
