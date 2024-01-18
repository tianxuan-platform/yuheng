package com.wuyiccc.yuheng.infrastructure.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;

/**
 * @author wuyiccc
 * @date 2023/9/24 16:12
 * <br>
 * 在mybatis-plus逻辑删除业务数据的时候, 自动将del_id字段设置为id字段
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class MybatisLogicDeleteInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        log.debug("进入mybatis自定义逻辑删除...");

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
        String sql = boundSql.getSql();
        log.debug("拦截的sql语句:" + sql);
        sql = sql.replace("SET  del_flag=1", "SET del_flag = 1, del_id = id");
        log.debug("修改之后的sql：" + sql);
        // 替换sql
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, sql);
        return invocation.proceed();
    }
}
