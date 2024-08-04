package com.wx.youqsd_manage.vo.req;

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
@Data
@ApiModel("用户请求对象")
public class UserInfoPageReq extends PageDTO implements Serializable {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phoneNo;

}
