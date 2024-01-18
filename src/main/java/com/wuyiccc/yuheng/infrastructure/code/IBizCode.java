package com.wuyiccc.yuheng.infrastructure.code;

/**
 * @author wuyiccc
 * @date 2024/1/16 22:08
 * <br>
 * 统一响应状态码基础接口
 * <br>
 * 状态码规则 x_y : x代表业务分类 y代表具体状态码
 */
public interface IBizCode {

    String getCode();

    String getMsg();
}
