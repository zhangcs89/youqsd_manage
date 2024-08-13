package com.wx.youqsd_manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.vo.req.ShopInfoPageReq;
import com.wx.youqsd_manage.vo.req.UserInfoPageReq;
import com.wx.youqsd_manage.vo.req.UserLoginReq;
import com.wx.youqsd_manage.vo.resp.ShopInfoPageResp;
import com.wx.youqsd_manage.vo.resp.UserInfoPageResp;

/**
 * @ClassName IUserService
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 12:56
 * @Version 1.0
 */
public interface IUserService extends IService<UserInfo> {


    public UserInfo backLogin(UserLoginReq req) throws DefineException;


    /**
     * 新增
     * @param userInfo
     */
    public void insert(UserInfo userInfo) throws DefineException ;

    /**
     * 变更
     * @param userInfo
     */
    public void mod(UserInfo userInfo);

    /**
     * 店铺
     * @param id
     */
    public void delete(int id);

    /**
     * 列表
     * @param req
     * @return
     */
    public IPage<UserInfoPageResp> findPageList(UserInfoPageReq req);

    /**
     * 微信登录
     * @param code
     * @param opCode
     * @return
     */
    public UserInfo  wxLogin(String code,String opCode) ;
}
