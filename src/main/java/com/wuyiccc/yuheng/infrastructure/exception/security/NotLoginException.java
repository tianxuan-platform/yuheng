package com.wuyiccc.yuheng.infrastructure.exception.security;

import com.wuyiccc.yuheng.infrastructure.code.IBizCode;
import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;

/**
 * @author wuyiccc
 * @date 2024/1/28 11:47
 */
public class NotLoginException extends CustomException {

    public NotLoginException() {
        super(YuhengBizCode.ERROR_USER_NOT_LOGIN);
    }
}
