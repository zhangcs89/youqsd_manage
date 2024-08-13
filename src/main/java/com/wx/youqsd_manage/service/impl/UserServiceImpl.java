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

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.loginUrl}")
    private String wxLoginUrl;

    @Value("${wx.getphonenumber}")
    private String phonenumberUrl;

    @Resource
    UserMapper userMapper;

    @Resource
    UserShopRelMapper userShopRelMapper;
//
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


    @Override
    public UserInfo wxLogin(String code, String opCode) {
        String url = wxLoginUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + opCode + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 解析微信服务器的响应
        String responseBody = response.getBody();
        System.out.println("获取opid返回值+++++++++++++++++++++++++++" + responseBody);
        //提取openid和session_key
        Map<String, String> wxResponse = parseWxResponse(responseBody);
        String openid = wxResponse.get("openid");
        String session_key = wxResponse.get("session_key");

        String str = getPhonNo(code);
        JSONObject jsonObject = JSON.parseObject(str);

        UserInfo user = new UserInfo();
        //调用小程序获取手机号,并插入新的数据
        if (jsonObject.getInteger("errcode") == 0) {
            Map phoneInfo = jsonObject.getObject("phone_info", Map.class);
            // 检查数据库中是否已经有一个与手机号关联的用户
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone_no", phoneInfo.get("phoneNumber"));
            user = userMapper.selectOne(queryWrapper);
            if (user == null) {
                Object phoneNumber = phoneInfo.get("phoneNumber");
                user = new UserInfo();
                user.setOpenId(openid);
                user.setUserType("2");
                user.setPhoneNo(phoneNumber.toString());
                userMapper.insert(user);
                //查询普通员工绑定的店铺信息
                UserShopRel rel = new UserShopRel();
                rel.setPhoneNo(phoneNumber.toString());
                QueryWrapper<UserShopRel> wrapper = new QueryWrapper<>();
                wrapper.eq("phone_no", phoneNumber.toString());
                List<UserShopRel> userShopRels = userShopRelMapper.selectList(wrapper);
                if (userShopRels != null && userShopRels.size() == 1) {
                    user.setShopId(userShopRels.get(0).getShopId());
                }

            }
        }
        String token = JwtUtils.generateJWT(user.getPhoneNo());
        user.setToken(token);
        return user;
    }

    private Map<String, String> parseWxResponse(String responseBody) {
        Map<String, String> wxResponse = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(responseBody);
        wxResponse.put("openid", jsonObject.getString("openid"));
        wxResponse.put("session_key", jsonObject.getString("session_key"));
        return wxResponse;
    }

    private String getAccessToken() {
        String tokenUrl = String.format(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                appId, appSecret);
        JSONObject token = JSON.parseObject(HttpUtil.get(tokenUrl));
//        String accessToken = (String) redisTemplate.opsForValue().get("access_token");
//        System.out.println("accessToken+++++++++++++++++++"+accessToken);
        System.out.println("accessToken+++++++++++++++++++" + token.getString("access_token"));
//        if(accessToken == null){
//            accessToken = token.getString("access_token");
//            redisTemplate.opsForValue().set("access_token", accessToken, 2, TimeUnit.HOURS);
//        }
        return token.getString("access_token");
    }

    private String getPhonNo(String code) {
        //通过appid和secret来获取token
        String accessToken = getAccessToken();
        //通过token和code来获取用户手机号
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        //封装请求体
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", code);
        //封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(paramMap, headers);
        //通过RestTemplate发送请求，获取到用户手机号码
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        String responseBody = response.getBody();
        System.out.println("获取手机号码+++++++++++++++++++++++++++" + responseBody);
        //返回到前端展示
        return response.getBody();
    }

}
