package com.wuyiccc.yuheng.infrastructure.exception;


import com.wuyiccc.yuheng.infrastructure.code.ABizCode;
import com.wuyiccc.yuheng.infrastructure.code.IBizCode;
import lombok.Getter;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:54
 * <br>
 * 自定义业务异常
 */
@Getter
public class CustomException extends RuntimeException {


    /**
     * 业务异常编码
     */
    private final IBizCode bizCode;

    public CustomException(IBizCode bizCode) {
        super(bizCode.getMsg());
        this.bizCode = bizCode;
    }

    public CustomException(String msg, Throwable e) {
        super(msg, e);
        this.bizCode = ABizCode.FAIL;
    }

    public CustomException(IBizCode bizCode, String msg) {
        super(msg);
        this.bizCode = bizCode;
    }


    public CustomException(IBizCode bizCode, String msg, Throwable throwable) {
        super(msg, throwable);
        this.bizCode = bizCode;
    }

    public CustomException(String message) {
        super(message);
        this.bizCode = ABizCode.FAIL;
    }

}
