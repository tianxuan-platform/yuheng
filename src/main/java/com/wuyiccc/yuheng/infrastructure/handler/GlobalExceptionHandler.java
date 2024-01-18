package com.wuyiccc.yuheng.infrastructure.handler;

import cn.dev33.satoken.exception.SaTokenException;
import com.wuyiccc.yuheng.infrastructure.code.ABizCode;
import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;
import com.wuyiccc.yuheng.infrastructure.pojo.R;
import com.wuyiccc.yuheng.infrastructure.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:59
 * <br>
 * 全局异常拦截器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public R<String> customExceptionHandler(ServletWebRequest request, CustomException e) {

        printErrorLog(request, e);
        if (StringUtils.isBlank(e.getMessage())) {
            return R.fail(e.getBizCode());
        } else {
            return R.fail(e.getBizCode(), e.getMessage());
        }
    }

    @ExceptionHandler(SaTokenException.class)
    public R<String> saTokenExceptionHandler(ServletWebRequest request, SaTokenException e) {
        printErrorLog(request, e);
        return R.fail(YuhengBizCode.SA_TOKEN_EXCEPTION, e.getMessage());
    }



    @ExceptionHandler(DuplicateKeyException.class)
    public R<String> duplicateKeyExceptionHandler(ServletWebRequest request, DuplicateKeyException e) {
        printErrorLog(request, e);
        return R.fail(ABizCode.DATA_REPEAT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Map<String, String>> methodArgumentNotValidExceptionHandler(ServletWebRequest request, MethodArgumentNotValidException e) {

        printErrorLog(request, e);

        BindingResult result = e.getBindingResult();
        Map<String, String> errors = getErrors(result);
        String msg = StringUtils.EMPTY;
        if (!errors.values().isEmpty()) {
            msg = errors.values().toArray(new String[]{})[0];
        }
        return R.fail(ABizCode.INVALID_PARAM, msg);
    }

    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(ServletWebRequest request, Exception e) {
        printErrorLog(request, e);
        return R.fail(ABizCode.FAIL);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 错误字段名称
            String fieldName = error.getField();
            // 错误信息
            String errMsg = error.getDefaultMessage();
            errorMap.put(fieldName, errMsg);
        }
        return errorMap;
    }

    private void printErrorLog(ServletWebRequest request, Exception ex) {

        try {
            String parameters = "";
            parameters = JsonUtils.objectToJson(request.getParameterMap());
            if (log.isErrorEnabled()) {
                log.error("统一异常拦截日志打印 -> uri= {} params={} ",
                        request.getHttpMethod() + " : " + request.getRequest().getRequestURI(),
                        parameters,
                        ex
                );
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("打印异常错误:{}", ex.getMessage(), e);
            }
        }
    }




}
