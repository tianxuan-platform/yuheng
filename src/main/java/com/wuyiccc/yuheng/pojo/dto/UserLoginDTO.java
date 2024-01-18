package com.wuyiccc.yuheng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wuyiccc
 * @date 2023/9/15 21:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    /**
     * 登录用户名
     */
    @NotNull(message = "username不能为null")
    private String username;

    /**
     * 登录用户密码
     */
    @NotNull(message = "password不能为null")
    private String password;
}
