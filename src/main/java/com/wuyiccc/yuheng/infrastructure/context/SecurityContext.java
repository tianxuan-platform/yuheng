package com.wuyiccc.yuheng.infrastructure.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.wuyiccc.yuheng.infrastructure.constants.ThreadLocalParamConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuyiccc
 * @date 2024/1/17 22:20
 * <br>
 * 用户信息线程上下文容器
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContext {

    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value);
    }

    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        return (T) map.getOrDefault(key, null);
    }


    public static void setUserId(String userId) {
        set(ThreadLocalParamConstants.USER_ID, userId);
    }

    public static String getUserId() {
        return get(ThreadLocalParamConstants.USER_ID, String.class);
    }

    public static void setUserName(String userName) {
        set(ThreadLocalParamConstants.USERNAME, userName);
    }

    public static String getUserName() {
        return get(ThreadLocalParamConstants.USERNAME, String.class);
    }

    public static void setNickName(String nickName) {
        set(ThreadLocalParamConstants.NICKNAME, nickName);
    }

    public static String getNickName() {
        return get(ThreadLocalParamConstants.NICKNAME, String.class);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
