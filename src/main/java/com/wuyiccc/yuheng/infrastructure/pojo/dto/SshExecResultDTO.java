package com.wuyiccc.yuheng.infrastructure.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/2/23 22:54
 * 命令执行返回结果
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SshExecResultDTO {


    private List<String> msgList;

}
