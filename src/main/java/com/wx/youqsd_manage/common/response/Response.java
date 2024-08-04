package com.wx.youqsd_manage.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Response
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 18:24
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Response<T> implements Serializable {

    private int code = 200;

    private String msg;

    private T data;

    public Response(){

    }
    public Response(String message, int i) {
    }

    @JsonIgnore
    public Boolean isSuccess() {
        return code == 200;
    }
}
