package com.wuyiccc.yuheng.infrastructure.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuyiccc
 * @date 2024/1/16 22:26
 * <br>
 * 通用业务规则状态码
 * <br>
 * x = 0 代表通用业务规则状态码
 */
@AllArgsConstructor
@Getter
public enum ABizCode implements IBizCode {

    OK("0_0", "操作成功"),

    FAIL("0_1", "操作失败"),

    DATA_REPEAT("0_2", "数据重复"),

    INVALID_PARAM("0_3", "参数校验失败"),

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
