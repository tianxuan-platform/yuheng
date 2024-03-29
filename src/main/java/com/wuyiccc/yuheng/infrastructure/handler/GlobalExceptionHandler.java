package com.wuyiccc.yuheng.infrastructure.handler;

import com.wuyiccc.yuheng.infrastructure.code.ABizCode;
import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;
import com.wuyiccc.yuheng.infrastructure.exception.security.NotLoginException;
import com.wuyiccc.yuheng.infrastructure.pojo.R;
import com.wuyiccc.yuheng.infrastructure.utils.JsonUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.common.SshException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(NotLoginException.class)
    public R<String> notLoginException(ServletWebRequest request, NotLoginException e) {
        printErrorLog(request, e);
        return R.fail(e.getBizCode());
    }

    @ExceptionHandler({ExpiredJwtException.class
            , UnsupportedJwtException.class
            , MalformedJwtException.class
            , SignatureException.class
    })
    public R<String> returnSignatureException(ServletWebRequest request, JwtException e) {
        printErrorLog(request, e);
        return R.fail(YuhengBizCode.JWT_CHECK_FAILED);
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


    @ExceptionHandler(BindException.class)
    public R<Map<String, String>> bindExceptionHandler(ServletWebRequest request, BindException e) {

        printErrorLog(request, e);

        BindingResult result = e.getBindingResult();
        Map<String, String> errors = getErrors(result);
        String msg = StringUtils.EMPTY;
        if (!errors.values().isEmpty()) {
            msg = errors.values().toArray(new String[]{})[0];
        }
        return R.fail(ABizCode.INVALID_PARAM, msg);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<String> maxUploadSizeExceededExceptionHandler(ServletWebRequest request, MaxUploadSizeExceededException e) {
        printErrorLog(request, e);

        return R.fail(ABizCode.FILE_SIZE_EXCEEDS_LIMIT);
    }

    @ExceptionHandler(SshException.class)
    public R<String> sshExceptionHandler(ServletWebRequest request, SshException e) {
        printErrorLog(request, e);

        return R.fail(ABizCode.FAIL, e.getMessage());
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
            String parameters;
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
