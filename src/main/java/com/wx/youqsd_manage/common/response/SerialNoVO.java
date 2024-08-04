package com.wx.youqsd_manage.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName SerialNoVO
 * @Description 查询用，显示虚拟序号
 * @Author zhangcs
 * @Date 2024/8/4 16:03
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("查询用的通用结构")
public class SerialNoVO implements Serializable {

    @ApiModelProperty("序号")
    private Integer serialNo;
}
