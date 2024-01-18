package com.wuyiccc.yuheng.infrastructure.pojo;

import com.wuyiccc.yuheng.infrastructure.code.ABizCode;
import com.wuyiccc.yuheng.infrastructure.code.IBizCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 * <br>
 * 统一接口返回体
 */
@Data
public class R<T> {

    // 响应业务状态码
    private String code;

    // 响应消息
    private String msg;


    // 响应数据
    private T data;

    private R() {
    }

    private R(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private static <T> R<T> r(IBizCode bizCode, String customMsg, T data) {

        String msg;
        if (StringUtils.isBlank(customMsg)) {
            msg = bizCode.getMsg();
        } else {
            msg = customMsg;
        }

        return new R<>(bizCode.getCode(), msg, data);
    }

    public static <T> R<T> ok() {

        return r(ABizCode.OK, null, null);
    }

    public static <T> R<T> ok(T data) {
        return r(ABizCode.OK, null, data);
    }

    public static <T> R<T> ok(String msg) {
        return r(ABizCode.OK, msg, null);
    }

    public static <T> R<T> ok(String msg, T data) {
        return r(ABizCode.OK, msg, data);
    }

    public static <T> R<T> data(T data) {
        return r(ABizCode.OK, null, data);
    }

    public static <T> R<T> fail() {
        return r(ABizCode.FAIL, null, null);
    }

    public static <T> R<T> fail(String msg) {

        return r(ABizCode.FAIL, msg, null);
    }

    public static <T> R<T> fail(IBizCode bizCode) {
        return r(bizCode, null, null);
    }


    public static <T> R<T> fail(IBizCode bizCode, String msg) {
        return r(bizCode, msg, null);
    }

}
