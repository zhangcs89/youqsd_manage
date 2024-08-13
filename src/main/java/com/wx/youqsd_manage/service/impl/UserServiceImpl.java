package com.wx.youqsd_manage.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.common.exception.ErrcodeStatus;
import com.wx.youqsd_manage.common.util.JwtUtils;
import com.wx.youqsd_manage.common.util.VirtualSerialNo;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.entity.UserShopRel;
import com.wx.youqsd_manage.mappper.UserMapper;
import com.wx.youqsd_manage.mappper.UserShopRelMapper;
import com.wx.youqsd_manage.service.IUserService;
import com.wx.youqsd_manage.vo.req.UserInfoPageReq;
import com.wx.youqsd_manage.vo.req.UserLoginReq;
import com.wx.youqsd_manage.vo.resp.UserInfoPageResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


//    @Autowired
//    private RedisTemplate redisTemplate;

    @Override
    public UserInfo backLogin(UserLoginReq req) throws DefineException {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", req.getUsername());
        queryWrapper.eq("password", req.getPassword());
        List<UserInfo> userInfos = userMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(userInfos)) {
            throw new DefineException(ErrcodeStatus.USERNAME_PASS_ERROR);
        }
        UserInfo userInfo = userInfos.get(0);
        String token = JwtUtils.generateJWT(userInfo.getPhoneNo());
        userInfo.setToken(token);
        return userInfo;

    }

    @Override
    public void insert(UserInfo userInfo) throws DefineException {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_no", userInfo.getPhoneNo());
        UserInfo info = userMapper.selectOne(queryWrapper);
        if (info != null) {
            throw new DefineException(ErrcodeStatus.PHONE_EXIST_ERROR);
        }
        userInfo.setUserType("1");
        userMapper.insert(userInfo);
    }

    @Override
    public void mod(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_no", userInfo.getPhoneNo());
        UserInfo info = userMapper.selectOne(queryWrapper);
        if (info != null && (info.getUserId().equals(userInfo.getId()))) {
            throw new DefineException(ErrcodeStatus.PHONE_EXIST_ERROR);
        }
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
        queryWrapper.eq("user_type",2);
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
        for (int i = 0; i < records.size(); i++) {
            if (!("admin").equals(records.get(i).getUserName())) {
                UserInfoPageResp resp = new UserInfoPageResp();
                BeanUtils.copyProperties(records.get(i), resp);
                respList.add(resp);
            }
        }
        pageNew.setTotal(respList.size());
        pageNew.setSize(userInfoPage.getSize());
        pageNew.setPages(userInfoPage.getPages());
        pageNew.setRecords(respList);
        return VirtualSerialNo.generate(pageNew);
    }




}
