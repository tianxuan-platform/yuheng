package com.wuyiccc.yuheng.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/9/15 22:24
 */
@Data
public class UserVO {

    /**
     * 用户id
     */
    private String id;

    /**
     * 登录名
     */
    private String username;


    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String faceUrl;

    /**
     * 用户创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime gmtCreate;

}
