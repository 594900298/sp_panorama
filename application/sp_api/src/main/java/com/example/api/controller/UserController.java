package com.example.api.controller;

import com.example.api.service.UserService;
import com.example.common.po.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ApiUserController")
@RequestMapping("/user")
@Api(tags = "用户信息")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("获取统计数据")
    @GetMapping("/getCount")
    public ResultData<UserCountVO> getCount() {
        return ResultData.success(userService.getCount());
    }
}
