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

import java.util.Date;

/**
 * @ClassName ShopInfo
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 10:56
 * @Version 1.0
 */
@Data
@TableName(value = "t_shop_info")
@ApiModel("店铺实体类")
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfo {

    @ApiModelProperty("店铺id，修改必填")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "店铺地址",required = true)
    @TableField(value="shop_addr")
    private String shopAddr;

    @ApiModelProperty(value = "店铺名称",required = true)
    @TableField(value="shop_name")
    private String shopName;

    @ApiModelProperty(value = "联系人",required = true)
    @TableField(value="contact_name")
    private String contactName;

    @ApiModelProperty(value = "联系电话",required = true)
    @TableField(value="contact_phone")
    private String contactPhone;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="update_time")
    private Date  updateTime;
}
