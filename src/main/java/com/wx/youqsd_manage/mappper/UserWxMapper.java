package com.wx.youqsd_manage.mappper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.entity.UserInfoWx;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserWxMapper
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/13 22:18
 * @Version 1.0
 */
@Mapper
public interface UserWxMapper extends BaseMapper<UserInfoWx> {
}
