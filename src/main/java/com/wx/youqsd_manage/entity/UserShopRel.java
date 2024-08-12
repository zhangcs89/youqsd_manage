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
 * @author zhangchunsheng
 * @version 1.0.0
 * @className UserShopRel
 * @date 2024/8/12   17:52
 * @description TODO
 */
@Data
@TableName(value = "t_user_shop_rel")
@ApiModel("用户店铺关联")
@AllArgsConstructor
@NoArgsConstructor
public class UserShopRel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value="shop_id")
    private Integer shopId;

    @TableField(value="phone_no")
    private String phoneNo;


}