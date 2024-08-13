package com.wx.youqsd_manage.vo.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UsrInfoResp
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/13 22:39
 * @Version 1.0
 */
@Data
@ApiModel("用户信息返回实体")
@AllArgsConstructor
@NoArgsConstructor
public class UsrInfoResp {

    @ApiModelProperty(value = "手机号")
    private String phoneNo;

    @TableField(value="user_type")
    @ApiModelProperty(value = "用户类型1店铺管理员2其他")
    @JsonProperty("userType")
    private String userType;

    @ApiModelProperty(value = "token用与拦截请求")
    private String token;

    @ApiModelProperty(value = "普通用户绑定店铺id")
    private Integer shopId;
}
