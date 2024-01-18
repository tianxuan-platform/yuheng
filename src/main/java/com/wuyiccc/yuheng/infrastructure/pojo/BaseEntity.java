package com.wuyiccc.yuheng.infrastructure.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:41
 * <br>
 * 数据库基础实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;


    /**
     * 数据创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createId;

    /**
     * 数据更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


    /**
     * 数据更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateId;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 逻辑删除唯一性辅助标识, 默认-1, 被删除的时候设置为id的值
     */
    private String delId;

    /**
     * 乐观锁
     */
    @Version
    private Long version;
}
