package com.wx.youqsd_manage.controller.shop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.util.StringUtils;
import com.wx.youqsd_manage.YouqsdManageApplication;
import com.wx.youqsd_manage.common.constant.MimeConstant;
import com.wx.youqsd_manage.common.exception.ErrcodeStatus;
import com.wx.youqsd_manage.common.response.Response;
import com.wx.youqsd_manage.common.response.ResponseEntity;
import com.wx.youqsd_manage.entity.ShopInfo;
import com.wx.youqsd_manage.service.IShopService;
import com.wx.youqsd_manage.vo.req.ShopInfoPageReq;
import com.wx.youqsd_manage.vo.resp.ShopInfoPageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName ShopController
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 10:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/shop")
@Api(tags = "店铺相关")
@Slf4j
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private IShopService shopService;

    @ApiOperation(value = "新增店铺", notes = "新增店铺", httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("/addShop")
    public Response addShop(@Valid @RequestBody ShopInfo shopInfo) {

        shopService.insert(shopInfo);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "修改店铺", notes = "修改店铺", httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("/modShop")
    public Response modShop(@Valid @RequestBody ShopInfo shopInfo) {
        if (StringUtils.isEmptyOrWhitespaceOnly((shopInfo.getId()+""))
               ) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        shopService.mod(shopInfo);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "删除店铺", notes = "删除店铺", httpMethod = "GET", consumes = MimeConstant.JSON)
    @GetMapping("/delShop")
    public Response delShop(@RequestParam(value = "id", required = true) int id) {
        if (StringUtils.isEmptyOrWhitespaceOnly((id+""))
        ) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        shopService.delete(id);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "店铺列表分页", notes = "店铺列表分页（用户姓名、手机号、微信名称一个必填）",
            httpMethod = "POST", consumes = MimeConstant.JSON)
    @PostMapping("/shopList")
    public Response<IPage<ShopInfoPageResp>> shopList(@RequestBody ShopInfoPageReq req) {
        if (StringUtils.isEmptyOrWhitespaceOnly((req.getPhoneNo()))
                && StringUtils.isEmptyOrWhitespaceOnly((req.getUserName()))
                && StringUtils.isEmptyOrWhitespaceOnly((req.getWxName()))) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        IPage<ShopInfoPageResp> pageList = shopService.findPageList(req);
        return ResponseEntity.success(pageList);
    }

    @ApiOperation(value = "店铺详情", notes = "店铺详情",
            httpMethod = "GET", consumes = MimeConstant.JSON)
    @GetMapping("/shopDetail")
    public Response<ShopInfo> shopDetail(@RequestParam(value = "id", required = true) int id) {
        if (StringUtils.isEmptyOrWhitespaceOnly((id+""))
        ) {
            return ResponseEntity.fail(ErrcodeStatus.PARAM_ERROR);
        }
        ShopInfo shopInfo = shopService.getById(id);
        return ResponseEntity.success(shopInfo);
    }

}
