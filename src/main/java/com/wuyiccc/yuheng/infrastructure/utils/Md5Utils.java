package com.wuyiccc.yuheng.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

/**
 * @author wuyiccc
 * @date 2023/9/13 23:22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Md5Utils {

    /**
     * 加密函数
     * @param data 加密数据
     * @param slat 盐
     * @return 加密结果
     */
    public static String encrypt(String data, String slat) {
        String base = data + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    /**
     * 生成6位数字字符盐
     * @return 6位字符数字盐
     */
    public static String generateSlat() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }


}
