package com.wx.youqsd_manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.vo.req.ShopInfoPageReq;
import com.wx.youqsd_manage.vo.resp.ShopInfoPageResp;

/**
 * @ClassName IUserService
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 12:56
 * @Version 1.0
 */
public interface IShopService extends IService<ShopInfo> {


    /**
     * 店铺新增
     * @param shopInfo
     */
    public void insert(ShopInfo shopInfo);

    /**
     * 店铺变更
     * @param shopInfo
     */
    public void mod(ShopInfo shopInfo);

    /**
     * 店铺变更
     * @param id
     */
    public void delete(int id);

    /**
     * 店铺列表
     * @param shopInfoPageReq
     * @return
     */
    public IPage<ShopInfoPageResp> findPageList(ShopInfoPageReq shopInfoPageReq);
}
