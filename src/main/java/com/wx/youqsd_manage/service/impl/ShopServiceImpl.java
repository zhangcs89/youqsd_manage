package com.wx.youqsd_manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.youqsd_manage.common.util.VirtualSerialNo;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.entity.UserShopRel;
import com.wx.youqsd_manage.mappper.ShopMapper;
import com.wx.youqsd_manage.mappper.UserMapper;
import com.wx.youqsd_manage.service.IShopService;
import com.wx.youqsd_manage.vo.req.ShopInfoPageReq;
import com.wx.youqsd_manage.vo.resp.ShopInfoPageResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ShopServiceImpl
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 14:23
 * @Version 1.0
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, ShopInfo> implements IShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Resource
    ShopMapper shopMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(ShopInfo shopInfo) {
        shopInfo.setCreateTime(new Date());
        shopMapper.save(shopInfo);
        //插入关联表
        UserShopRel rel = new UserShopRel();
        rel.setPhoneNo(shopInfo.getPhoneNo());
        rel.setShopId(shopInfo.getId());
        shopMapper.insetRel(rel);
    }

    @Override
    public void mod(ShopInfo shopInfo) {
        shopInfo.setCreateTime(new Date());
        UpdateWrapper<ShopInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", shopInfo.getId());
        shopMapper.update(shopInfo, updateWrapper);
    }

    @Override
    public void delete(int id) {
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        shopMapper.delete(queryWrapper);
    }

    @Override
    public IPage<ShopInfoPageResp> findPageList(ShopInfoPageReq shopInfoPageReq) {
        IPage<ShopInfoPageResp> pageList = shopMapper.findPageList(
                shopInfoPageReq.page(), shopInfoPageReq);
        return  VirtualSerialNo.generate(pageList);
    }
}
