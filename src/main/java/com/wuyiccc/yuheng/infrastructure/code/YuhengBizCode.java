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

    SA_TOKEN_EXCEPTION("1_1", "sa-token权限异常"),

    USER_NOT_FOUND("1_2", "用户不存在"),

    USER_NAME_OR_PASSWORD_ERROR("1_3", "用户名或密码错误"),
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
