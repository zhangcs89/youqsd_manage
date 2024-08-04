package com.wx.youqsd_manage.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一定义错误码及信息
 *
 * @author lgy
 */
public enum ErrcodeStatus implements ICodeMessage {

    // 成功
    OK(Errcode.OK, "SUCCESS"),

    // 失效
    UNKNOW_ERROR(Errcode.UNKNOW_ERROR, "未知错误"),

    // 其他错误的定义
    PARAM_ERROR(Errcode.PARAM_ERROR, "参数错误"),

    USERNAME_PASS_ERROR(Errcode.USERNAME_PASS_ERROR, "用户名或密码错误");





    // 内部定义
    private final int code;
    private final String message;

    ErrcodeStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


    /**
     * 返回错误码默认代表的含义
     *
     * @param code
     * @return
     */
    public static String getDefaultErrorMessage(int code) {
        return errmsgMap.getOrDefault(code, "");
    }

    private static final Map<Integer, String> errmsgMap = new HashMap<>();

    static {
        for (ErrcodeStatus err : ErrcodeStatus.values()) {
            errmsgMap.put(err.getCode(), err.getMessage());
        }
    }
}
