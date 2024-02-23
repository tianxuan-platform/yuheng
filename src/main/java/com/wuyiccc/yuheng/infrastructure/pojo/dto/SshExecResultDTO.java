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
     * 执行结果是否正确
     */
    private boolean execResultFlag;

    /**
     * 返回信息
     */
    private String msg;
}
