package com.wuyiccc.yuheng.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wuyiccc
 * @date 2024/1/19 00:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserPasswordUpdateBO {

    /**
     * 用户密码
     */
    @NotNull(message = "用户密码不能为null")
    private String password;
}
