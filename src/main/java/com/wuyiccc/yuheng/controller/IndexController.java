package com.wuyiccc.yuheng.controller;

import com.wuyiccc.yuheng.infrastructure.code.ABizCode;
import com.wuyiccc.yuheng.infrastructure.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2024/1/28 12:54
 */
@Slf4j
@Validated
@RestController
public class IndexController {

    /**
     * 心跳接口
     */
    @GetMapping("/heartbeat")
    public R<String> login() {
        return R.data("heartbeat");
    }

    /**
     * 请求接口不存在的时候, mvc自动转发到该接口
     */
    @GetMapping("/error")
    @ResponseStatus(code = HttpStatus.OK)
    public R<String> getError() {
        return R.fail(ABizCode.API_NOT_FOUND);
    }

    @PostMapping("/error")
    @ResponseStatus(code = HttpStatus.OK)
    public R<String> postError() {
        return R.fail(ABizCode.API_NOT_FOUND);
    }

}
