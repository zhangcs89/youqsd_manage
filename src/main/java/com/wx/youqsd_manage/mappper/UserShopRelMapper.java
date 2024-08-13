package com.wx.youqsd_manage.mappper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.entity.UserShopRel;
import com.wx.youqsd_manage.vo.req.ShopInfoPageReq;
import com.wx.youqsd_manage.vo.resp.ShopInfoPageResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName ShopMapper
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 12:58
 * @Version 1.0
 */
@Mapper
public interface UserShopRelMapper extends BaseMapper<UserShopRel> {

}
