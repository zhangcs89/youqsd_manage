package com.wx.youqsd_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.entity.UserInfoWx;
import com.wx.youqsd_manage.vo.resp.UsrInfoResp;

/**
 * @ClassName IUserInfoWx
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/13 22:16
 * @Version 1.0
 */
public interface IUserWxService extends IService<UserInfoWx> {

    /**
     * 微信登录
     * @param code
     * @param opCode
     * @return
     */
    public UsrInfoResp wxLogin(String code, String opCode) ;
}
