package com.wx.youqsd_manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String userName;

    @TableField(value="password")
    @ApiModelProperty(value = "密码",required = false)
    private String password;

    @TableField(value="phone_no")
    @ApiModelProperty(value = "手机号",required = false)
    private String phoneNo;

    @TableField(value="wx_name")
    @ApiModelProperty(value = "微信名称",required = false)
    private String wxName;

    @TableField(value="user_type")
    @ApiModelProperty(value = "用户类型1店铺管理员2其他",required = false)
    private String userType;

    @TableField(value="user_id")
    @ApiModelProperty(value = "微信平台上都有一个唯一的用户ID",required = false)
    private String userId;

    @TableField(value="open_id")
    @ApiModelProperty(value = "OpenID是用户在公众号或小程序中的唯一标识",required = false)
    private String openId;

    @TableField(value="union_id")
    @ApiModelProperty(value = "UnionID是一个可以跨多个公众号或小程序的唯一标识",required = false)
    private String unionId;

}
