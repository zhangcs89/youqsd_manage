package com.wx.youqsd_manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.common.exception.ErrcodeStatus;
import com.wx.youqsd_manage.common.util.VirtualSerialNo;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.mappper.UserMapper;
import com.wx.youqsd_manage.service.IUserService;
import com.wx.youqsd_manage.vo.req.UserInfoPageReq;
import com.wx.youqsd_manage.vo.req.UserLoginReq;
import com.wx.youqsd_manage.vo.resp.UserInfoPageResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 12:57
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    UserMapper userMapper;

    @Override
    public void backLogin(UserLoginReq req) throws DefineException {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",req.getUserName());
        queryWrapper.eq("password",req.getPassword());
        List<UserInfo> userInfos = userMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(userInfos)){
            throw new DefineException(ErrcodeStatus.USERNAME_PASS_ERROR);
        }

    }

    @Override
    public void insert(UserInfo userInfo) {
        userInfo.setUserType("1");
        userMapper.insert(userInfo);
    }

    @Override
    public void mod(UserInfo userInfo) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userInfo.getId());
        userMapper.update(userInfo, updateWrapper);
    }

    @Override
    public void delete(int id) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        userMapper.delete(queryWrapper);
    }

    @Override
    public IPage<UserInfoPageResp> findPageList(UserInfoPageReq req) {
        Integer pageNum = req.getPageNum();
        Integer pageSize = req.getPageSize();
        Page<UserInfo> page = Page.of(pageNum, pageSize);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(req.getUserName())) {
            queryWrapper.like("user_name", req.getUserName());
        }
        if (StringUtils.isNotEmpty(req.getPhoneNo())) {
            queryWrapper.like("phone_no", req.getPhoneNo());
        }

        Page<UserInfo> userInfoPage = userMapper.selectPage(page, queryWrapper);
        List<UserInfo> records = userInfoPage.getRecords();
        Page<UserInfoPageResp> pageNew = new Page<>();
        List<UserInfoPageResp> respList = new ArrayList<>();
        for (UserInfo leadStatistics : records) {
            UserInfoPageResp resp = new UserInfoPageResp();
            BeanUtils.copyProperties(leadStatistics, resp);
            respList.add(resp);
        }
        pageNew.setTotal(userInfoPage.getTotal());
        pageNew.setSize(userInfoPage.getSize());
        pageNew.setPages(userInfoPage.getPages());
        pageNew.setRecords(respList);
        return VirtualSerialNo.generate(pageNew);
    }
}
