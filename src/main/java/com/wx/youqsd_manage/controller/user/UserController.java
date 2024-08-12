package com.wx.youqsd_manage.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.util.StringUtils;
import com.wx.youqsd_manage.common.constant.MimeConstant;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.common.exception.ErrcodeStatus;
import com.wx.youqsd_manage.common.response.Response;
import com.wx.youqsd_manage.common.response.ResponseEntity;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.entity.UserInfo;
import com.wx.youqsd_manage.service.IShopService;
import com.wx.youqsd_manage.service.IUserService;
import com.wx.youqsd_manage.vo.req.ShopInfoPageReq;
import com.wx.youqsd_manage.vo.req.UserInfoPageReq;
import com.wx.youqsd_manage.vo.req.UserInfoReq;
import com.wx.youqsd_manage.vo.resp.ShopInfoPageResp;
import com.wx.youqsd_manage.vo.resp.UserInfoPageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 19:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("/addUser")
    public Response addUser(@Valid @RequestBody UserInfo userInfo) throws DefineException {
        if (StringUtils.isEmptyOrWhitespaceOnly((userInfo.getPhoneNo()))
        ) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        userService.insert(userInfo);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "修改用户", notes = "修改用户", httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("/modUser")
    public Response modUser(@Valid @RequestBody UserInfo userInfo) {
        if (StringUtils.isEmptyOrWhitespaceOnly((userInfo.getId() + ""))
                || (StringUtils.isEmptyOrWhitespaceOnly((userInfo.getPhoneNo()))
        )) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        userService.mod(userInfo);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "GET", consumes = MimeConstant.JSON)
    @GetMapping("/delUser")
    public Response delUser(@RequestParam(value = "id", required = true) int id) {
        if (StringUtils.isEmptyOrWhitespaceOnly((id + ""))
        ) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        userService.delete(id);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "用户列表分页", notes = "用户列表分页",
            httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("/userList")
    public Response<IPage<UserInfoPageResp>> userList(@RequestBody UserInfoPageReq req) {
        IPage<UserInfoPageResp> pageList = userService.findPageList(req);
        return ResponseEntity.success(pageList);
    }
}
