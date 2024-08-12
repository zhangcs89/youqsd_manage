package com.wx.youqsd_manage.controller.login;


import com.wx.youqsd_manage.common.constant.MimeConstant;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.common.exception.ErrcodeStatus;
import com.wx.youqsd_manage.common.response.Response;
import com.wx.youqsd_manage.common.response.ResponseEntity;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.service.IUserService;
import com.wx.youqsd_manage.vo.req.UserLoginReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 *@title LoginConttroller
 *@description
 *@author zhangcs
 *@version 1.0
 *@create 2024/8/3 15:30
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登陆")
public class LoginConttroller {

    private static Logger logger = LoggerFactory.getLogger(LoginConttroller.class);


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "后台根据账号密码登陆", notes = "后台登陆", httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("back/login")
    public Response backLogin(@RequestBody UserLoginReq req) {
        try {
            userService.backLogin(req);
            return ResponseEntity.success("11111111111111111111111");
        } catch (DefineException e) {
            return ResponseEntity.fail(ErrcodeStatus.USERNAME_PASS_ERROR);
        }

    }

    @ApiOperation(value = "微信登录", notes = "微信登陆opcode获取opid的code，code是获取手机号的code", httpMethod = "POST", consumes = MimeConstant.JSON)
    @GetMapping("wx/login")
    public Response wxLogin(@RequestParam(value = "code", required = true) String code,
                            @RequestParam(value = "opCode", required = true) String opCode) {
        UserInfo userInfo = userService.wxLogin(code,opCode);
        return ResponseEntity.success(userInfo);
    }
}
