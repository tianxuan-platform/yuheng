package com.wuyiccc.yuheng.infrastructure.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2024/2/23 22:54
 * 命令执行返回结果
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SshExecResultDTO {


    /**
     * 正常输出流信息
     */
    private String msg;

    /**
     * 异常输出流信息
     */
    private String errorMsg;
}
