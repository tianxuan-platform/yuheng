package com.wuyiccc.yuheng.infrastructure.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuyiccc
 * @date 2024/1/16 23:36
 * <br>
 * 玉衡基础业务状态码
 * <br>
 * x = 1 代表玉衡基础业务异常状态码
 */
@AllArgsConstructor
@Getter
public enum YuhengBizCode implements IBizCode {

    ERROR_SA_TOKEN("1_1", "sa-token权限异常"),

    ERROR_USER_NOT_FOUND("1_2", "用户不存在"),

    ERROR_USER_NAME_OR_PASSWORD("1_3", "用户名或密码错误"),

    ERROR_NO_PERMISSION("1_4", "用户无操作权限"),

    ERROR_NOT_LOGIN("1_5", "用户未登录"),
    ;

    /**
     * 业务状态码
     */
    private final String code;

    /**
     * 业务消息
     */
    private final String msg;


}
