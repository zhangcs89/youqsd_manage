package com.wx.youqsd_manage.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName UserLogin
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 12:44
 * @Version 1.0
 */
@ApiModel("登陆请求对象")
public class UserLoginReq implements Serializable {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
