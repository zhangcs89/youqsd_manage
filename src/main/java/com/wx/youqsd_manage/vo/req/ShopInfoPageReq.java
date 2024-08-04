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
@Data
@ApiModel("店铺请求对象")
public class ShopInfoPageReq extends PageDTO implements Serializable {

    @ApiModelProperty(name = "shopAddr",value = "店铺地址")
    private String shopAddr;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("联系人")
    private String contactName;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phoneNo;

    @ApiModelProperty("微信名称")
    private String wxName;

}
