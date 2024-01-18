package com.wuyiccc.yuheng.infrastructure.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.context.SecurityContext;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;
import com.wuyiccc.yuheng.infrastructure.utils.SpringUtils;
import com.wuyiccc.yuheng.pojo.entity.UserEntity;
import com.wuyiccc.yuheng.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/17 23:26
 * <br>
 * 拦截登录用户, 将用户信息设置到线程上下文中
 */
public class YuHengSecurityContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserService userService = SpringUtils.getBean(UserService.class);

        String userId = StpUtil.getLoginIdAsString();
        UserEntity userEntity = userService.findUserEntityById(userId);
        if (Objects.isNull(userEntity)) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }
        SecurityContext.setUserId(userEntity.getId());
        SecurityContext.setUserName(userEntity.getUsername());
        SecurityContext.setNickName(userEntity.getNickname());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContext.remove();
    }
}
