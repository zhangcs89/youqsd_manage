package com.wx.youqsd_manage.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wx.youqsd_manage.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ShopInfoReq
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 15:36
 * @Version 1.0
 */
@ApiModel("店铺请求对象")
public class ShopInfoPageReq extends PageDTO implements Serializable {

    @ApiModelProperty(name = "shopAddr",value = "店铺地址")
    @JsonProperty("shopAddr")
    private String shopAddr;

    @ApiModelProperty("店铺名称")
    @JsonProperty("shopName")
    private String shopName;

    @ApiModelProperty("联系人")
    @JsonProperty("contactName")
    private String contactName;

    @ApiModelProperty("联系电话")
    @JsonProperty("contactPhone")
    private String contactPhone;

    @ApiModelProperty("用户姓名")
    @JsonProperty("userName")
    private String userName;

    @ApiModelProperty("手机号")
    @JsonProperty("phoneNo")
    private String phoneNo;

    @ApiModelProperty("微信名称")
    @JsonProperty("wxName")
    private String wxName;

    @ApiModelProperty(value = "登录人类型",required = true)
    @JsonProperty("userType")
    private String userType;

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

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

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
