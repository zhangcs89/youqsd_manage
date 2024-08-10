package com.wx.youqsd_manage.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wx.youqsd_manage.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ShopInfoReq
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 15:36
 * @Version 1.0
 */
@ApiModel("用户请求对象")
public class UserInfoPageReq extends PageDTO implements Serializable {

    @ApiModelProperty("用户姓名")
    @JsonProperty("userName")
    private String userName;

    @ApiModelProperty("手机号")
    @JsonProperty("phoneNo")
    private String phoneNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
