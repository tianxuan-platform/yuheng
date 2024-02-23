package com.wuyiccc.yuheng.infrastructure.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2024/2/23 22:58
 * ssh连接信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SshConnectionConfigDTO {

    /**
     * 主机地址
     */
    private String host;

    /**
     * 主机端口
     */
    private Integer port;

    /**
     * 主机用户名
     */
    private String username;

    /**
     * 主机密码
     */
    private String password;
}
