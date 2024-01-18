package com.wuyiccc.yuheng.infrastructure.utils;

import com.wuyiccc.yuheng.infrastructure.constants.DbConstants;
import com.wuyiccc.yuheng.infrastructure.context.SecurityContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/17 23:23
 * <br>
 * 用户线程上下文信息获取工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static String getUserId() {
        return SecurityContext.getUserId();
    }

    public static String getUserName() {
        return SecurityContext.getUserName();
    }

    public static String getNickName() {
        return SecurityContext.getNickName();
    }

    public static boolean isAdmin() {
        String userName = SecurityContext.getUserName();
        if (Objects.equals(userName, DbConstants.ADMIN_USERNAME)) {
            return true;
        }
        return false;
    }
}