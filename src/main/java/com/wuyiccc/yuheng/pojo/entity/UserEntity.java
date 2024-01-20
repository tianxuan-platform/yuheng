package com.wuyiccc.yuheng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuyiccc.yuheng.infrastructure.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "t_user")
public class UserEntity extends BaseEntity {



    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户混合加密的盐
     */
    private String slat;

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
}
