package com.wx.youqsd_manage.common.exception;

/**
 * @ClassName UnifiedExceptionHandler
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/10 14:18
 * @Version 1.0
 */

import com.wx.youqsd_manage.common.response.Response;
import com.wx.youqsd_manage.common.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UnifiedExceptionHandler {
    /**
     * 业务异常处理
     *
     * @param businessException 业务异常信息
     */
    @ExceptionHandler(value = DefineException.class)
    public <T> Response<T> businessExceptionHandler(DefineException businessException) {
        log.error(businessException.getMessage(), businessException);
        return ResponseEntity.fail(businessException.getCode(), businessException.getMessage());
    }

    /**
     * 未知异常处理
     *
     * @param exception 异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public <T> Response<T> exceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.fail(ErrcodeStatus.UNKNOW_ERROR.getCode(), ErrcodeStatus.UNKNOW_ERROR.getMessage());
    }

    /**
     * 参数异常处理
     *
     * @param exception 异常信息
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public <T> Response<T> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR.getCode(), ErrcodeStatus.PARAM_ERROR.getMessage());
    }
}

