package com.wx.youqsd_manage.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ShopInfoReq
 * @Description 分页通用字段
 * @Author zhangcs
 * @Date 2024/8/4 15:36
 * @Version 1.0
 */
@ApiModel("基础查询参数，可以为空")
public class PageDTO implements Serializable {

    @ApiModelProperty(value = "页号", dataType = "int")
    @JsonProperty("pageNum")
    private Integer pageNum;


    @ApiModelProperty(value = "每页数量", dataType = "int")
    @JsonProperty("pageSize")
    private Integer pageSize;

//    @ApiModelProperty(value = "开始时间", dataType = "Date")
//    private Date startDate;
//
//    @ApiModelProperty(value = "结束时间", dataType = "Date")
//    private Date endDate;

    public <T> IPage<T> page(boolean doCount) {
        return Page.of(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize, doCount);
    }

    public <T> IPage<T> page() {
        return page(true);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
}
