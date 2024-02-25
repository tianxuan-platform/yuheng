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

    API_NOT_FOUND("0_4", "api接口不存在"),

    FILE_SIZE_EXCEEDS_LIMIT("0_5", "文件大小超出限制"),

    CONCURRENT_OPERATION("0_6", "并发操作, 请稍后再试"),
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
