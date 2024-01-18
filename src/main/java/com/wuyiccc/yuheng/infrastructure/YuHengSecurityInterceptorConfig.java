package com.wuyiccc.yuheng.infrastructure;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.wuyiccc.yuheng.infrastructure.interceptor.YuHengSecurityContextInterceptor;
import com.wuyiccc.yuheng.infrastructure.config.security.YuHengSecurityIgnoreConfig;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验, 不需要sa-token校验的接口必须加@SaIgnore注解忽略
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin())).addPathPatterns("/**")
                .excludePathPatterns(yuHengSecurityIgnoreConfig.getUrls());

        // 注册用户信息获取拦截器
        registry.addInterceptor(new YuHengSecurityContextInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(yuHengSecurityIgnoreConfig.getUrls());
    }

}
