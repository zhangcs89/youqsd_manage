package com.wx.youqsd_manage.controller;


import com.wx.youqsd_manage.common.response.Response;
import com.wx.youqsd_manage.common.response.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@title TestController
 *@description 测试类
 *@author zhangcs
 *@version 1.0
 *@create 2024/8/3 15:29
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation(value = "Find user by ID", notes = "Returns a single user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/test")
    public Response test() {
        return ResponseEntity.success();
    }


}
