package com.wx.youqsd_manage.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.youqsd_manage.common.util.JwtUtils;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.entity.UserInfoWx;
import com.wx.youqsd_manage.entity.UserShopRel;
import com.wx.youqsd_manage.mappper.UserMapper;
import com.wx.youqsd_manage.mappper.UserShopRelMapper;
import com.wx.youqsd_manage.mappper.UserWxMapper;
import com.wx.youqsd_manage.service.IUserWxService;
import com.wx.youqsd_manage.vo.resp.UsrInfoResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserWxServiceImpl
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/13 22:17
 * @Version 1.0
 */
@Service
public class UserWxServiceImpl extends ServiceImpl<UserWxMapper, UserInfoWx> implements IUserWxService {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.loginUrl}")
    private String wxLoginUrl;

    @Value("${wx.getphonenumber}")
    private String phonenumberUrl;
    @Resource
    UserWxMapper userWxMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserShopRelMapper userShopRelMapper;
//

    @Override
    public UsrInfoResp wxLogin(String code, String opCode) {
        UsrInfoResp resp = new UsrInfoResp();
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

        UserInfoWx userInfoWx = new UserInfoWx();
        UserInfo userInfo = new UserInfo();
        //调用小程序获取手机号,并插入新的数据
        if (jsonObject.getInteger("errcode") == 0) {
            Map phoneInfo = jsonObject.getObject("phone_info", Map.class);
            // 先判断是不是管理员
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone_no", phoneInfo.get("phoneNumber"));
            userInfo = userMapper.selectOne(queryWrapper);
            if (userInfo == null) {
                //判断普通员工是不是已经注册
                QueryWrapper<UserInfoWx> wrapper = new QueryWrapper<>();
                wrapper.eq("phone_no", phoneInfo.get("phoneNumber"));
                userInfoWx = userWxMapper.selectOne(wrapper);
                if (userInfoWx == null) {
                    //没有注册插入wx员工表
                    Object phoneNumber = phoneInfo.get("phoneNumber");
                    userInfoWx = new UserInfoWx();
                    userInfoWx.setOpenId(openid);
                    userInfoWx.setUserType("2");
                    userInfoWx.setPhoneNo(phoneNumber.toString());
                    userWxMapper.insert(userInfoWx);
                    //查询普通员工绑定的店铺信息
                    QueryWrapper<UserShopRel> qw = new QueryWrapper<>();
                    qw.eq("phone_no", phoneNumber.toString());
                    List<UserShopRel> userShopRels = userShopRelMapper.selectList(qw);
                    if (userShopRels != null && userShopRels.size() == 1) {
                        userInfoWx.setShopId(userShopRels.get(0).getShopId());
                    }
                } else {
                    resp.setPhoneNo(userInfoWx.getPhoneNo());
                    resp.setUserType(userInfoWx.getUserType());
                    //查询普通员工绑定的店铺信息
                    QueryWrapper<UserShopRel> qw = new QueryWrapper<>();
                    qw.eq("phone_no", userInfoWx.getPhoneNo());
                    List<UserShopRel> userShopRels = userShopRelMapper.selectList(qw);
                    if (userShopRels != null && userShopRels.size() == 1) {
                        resp.setShopId(userShopRels.get(0).getShopId());
                    }
                }
            } else {
                resp.setPhoneNo(userInfo.getPhoneNo());
                resp.setUserType(userInfo.getUserType());
            }
        }
        String token = JwtUtils.generateJWT(userInfoWx.getPhoneNo());
        resp.setToken(token);
        return resp;
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
