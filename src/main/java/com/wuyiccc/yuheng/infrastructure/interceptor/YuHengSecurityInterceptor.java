package com.wuyiccc.yuheng.infrastructure.interceptor;

import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.config.security.YuHengSecurityIgnoreConfig;
import com.wuyiccc.yuheng.infrastructure.constants.SecurityConstants;
import com.wuyiccc.yuheng.infrastructure.context.SecurityContext;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;
import com.wuyiccc.yuheng.infrastructure.exception.security.NotLoginException;
import com.wuyiccc.yuheng.infrastructure.utils.JsonUtils;
import com.wuyiccc.yuheng.infrastructure.utils.JwtUtils;
import com.wuyiccc.yuheng.infrastructure.utils.SpringUtils;
import com.wuyiccc.yuheng.pojo.entity.UserEntity;
import com.wuyiccc.yuheng.pojo.vo.UserVO;
import com.wuyiccc.yuheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/17 23:26
 * <br>
 * 拦截登录用户, 将用户信息设置到线程上下文中
 */
@Slf4j
public class YuHengSecurityInterceptor implements HandlerInterceptor {

    private YuHengSecurityIgnoreConfig yuHengSecurityIgnoreConfig;

    public YuHengSecurityInterceptor(YuHengSecurityIgnoreConfig yuHengSecurityIgnoreConfig) {
        this.yuHengSecurityIgnoreConfig = yuHengSecurityIgnoreConfig;
    }

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("YuHengSecurityInterceptor拦截器 拦截请求接口为: {}", request.getRequestURL());

        UserService userService = SpringUtils.getBean(UserService.class);

        // 1. 校验是否需要拦截该接口进行用户权限校验
        List<String> ignoreUrls = yuHengSecurityIgnoreConfig.getUrls();
        String url = request.getRequestURI();
        if (CollectionUtils.isNotEmpty(ignoreUrls)) {
            for (String excludeUrl : ignoreUrls) {
                if (antPathMatcher.matchStart(excludeUrl, url)) {
                    return true;
                }
            }
        }

        // 2. 需要进行用户权限校验
        String token = request.getHeader(SecurityConstants.HTTP_HEADER_TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            throw new NotLoginException();
        }

        String[] tokenArr = token.split(JwtUtils.AT);
        if (tokenArr.length < 2) {
            throw new NotLoginException();
        }

        // 获得jwt的令牌与前缀
        String prefix = tokenArr[0];
        String jwt = tokenArr[1];

        if (!JwtUtils.ADMIN_PREFIX.equals(prefix)) {
            throw new NotLoginException();
        }
        String userJson = JwtUtils.checkJwt(jwt);


        UserVO userVO = JsonUtils.jsonToPojo(userJson, UserVO.class);

        UserEntity userEntity = userService.findUserEntityById(userVO.getId());
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
