package com.wx.youqsd_manage.common.exception;

/**
 * @ClassName DefineException
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 18:43
 * @Version 1.0
 */
public class DefineException extends RuntimeException implements ICodeMessage {
    private static final long serialVersionUID = 2L;
    protected final int code;

    public DefineException(int code, String message) {
        super(message == null ? "" : message);
        this.code = code;
    }

    public DefineException(int code) {
        this(code, ErrcodeStatus.getDefaultErrorMessage(code));
    }


    public DefineException(ICodeMessage cm) {
        this(cm.getCode(), cm.getMessage());
    }

    @Override
    public int getCode() {
        return code;
    }

    public String toJson() {
        return "{\"code\":" + this.code + ", \"message\":\"DefineException: " +
                super.getMessage().replace('"', '\'') + "\"}";
    }

    @Override
    public String toString() {
        return "code: " + this.code + ", Message: " + super.getMessage();
    }
}
