package com.wuyiccc.yuheng.infrastructure.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wuyiccc.yuheng.infrastructure.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:33
 * mybatis-plus自动字段注入
 */
@Component
public class MybatisPlusHandlerConfig implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {


        // 设置操作时间
        this.setFieldValByName("gmtCreate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);

        // 设置操作用户
        String userId = SecurityUtils.getUserId();
        if (Objects.isNull(userId)) {
            userId = StringUtils.EMPTY;
        }
        this.setFieldValByName("updateId", userId, metaObject);
        this.setFieldValByName("createId", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        // 设置操作用户
        String userId = SecurityUtils.getUserId();
        if (Objects.isNull(userId)) {
            userId = StringUtils.EMPTY;
        }

        this.setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateId", userId, metaObject);
    }
}
