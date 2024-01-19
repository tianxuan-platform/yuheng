package com.wuyiccc.yuheng.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wuyiccc
 * @date 2023/9/16 10:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdateBO {

    @NotNull(message = "用户id不能为null")
    private String id;

    /**
     * 用户密码
     */
    @NotNull(message = "用户密码不能为null")
    private String password;
}
