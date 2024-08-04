package com.wx.youqsd_manage.common.response;

import com.wx.youqsd_manage.common.exception.ICodeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ResponseEntity
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 18:25
 * @Version 1.0
 */
public class ResponseEntity {
    protected final static Logger logger = LoggerFactory.getLogger(ResponseEntity.class);

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setCode(200);
        return response;
    }

    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setMsg("success");
        return response;
    }

    public static <T> Response<T> fail(int code, String msg) {
        logger.error("errcode={}, msg={}", code, msg);
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }


    public static <T> Response<T> fail(int code, String msg, T data) {
        logger.error("errcode={}, msg={}", code, msg);
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }


    public static <T> Response<T> fail(ICodeMessage codeMessage) {
        return fail(codeMessage.getCode(), codeMessage.getMessage());
    }

    public static <T> Response<T> fail(ICodeMessage codeMessage, String userDefineMessage) {
        return fail(codeMessage.getCode(), userDefineMessage);
    }
}
