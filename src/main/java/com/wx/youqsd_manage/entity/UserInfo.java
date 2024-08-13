package com.wx.youqsd_manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 10:56
 * @Version 1.0
 */
@Data
@TableName(value = "t_user_info")
@ApiModel("用户实体类")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value="user_name")
    @ApiModelProperty(value = "用户名",required = false)
    @JsonProperty("pageSize")
    private String userName;

    @TableField(value="password")
    @ApiModelProperty(value = "密码",required = false)
    @JsonProperty("password")
    private String password;

    @TableField(value="phone_no")
    @ApiModelProperty(value = "手机号",required = false)
    @JsonProperty("phoneNo")
    private String phoneNo;

    @TableField(value="wx_name")
    @ApiModelProperty(value = "微信名称",required = false)
    @JsonProperty("wxName")
    private String wxName;

    @TableField(value="user_type")
    @ApiModelProperty(value = "用户类型1店铺管理员2其他",required = false)
    @JsonProperty("userType")
    private String userType;

    @TableField(value="user_id")
    @ApiModelProperty(value = "微信平台上都有一个唯一的用户ID",required = false)
    @JsonProperty("userId")
    private String userId;

    @TableField(value="open_id")
    @ApiModelProperty(value = "OpenID是用户在公众号或小程序中的唯一标识",required = false)
    @JsonProperty("openId")
    private String openId;

    @TableField(value="union_id")
    @ApiModelProperty(value = "UnionID是一个可以跨多个公众号或小程序的唯一标识",required = false)
    @JsonProperty("unionId")
    private String unionId;

    @JsonProperty("SessionKey")
    private String SessionKey;

    @ApiModelProperty(value = "token用与拦截请求",required = false)
    @JsonProperty("token")
    private String token;

}
