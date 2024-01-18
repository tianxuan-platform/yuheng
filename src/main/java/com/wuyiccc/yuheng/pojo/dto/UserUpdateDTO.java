package com.wuyiccc.yuheng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wuyiccc
 * @date 2023/9/15 22:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为null")
    private String id;


    /**
     * 用户昵称
     */
    @NotNull(message = "nickname不能为null")
    private String nickname;

    /**
     * 备注
     */
    @NotNull(message = "remark不能为null")
    private String remark;

    /**
     * 头像
     */
    @NotNull(message = "faceUrl不能为null")
    private String faceUrl;
}
