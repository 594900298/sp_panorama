package com.example.api.controller;

import com.example.api.service.LoginService;
import com.example.api.vo.LoginVO;
import com.example.common.vo.ResultDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("ApiLoginController")
@RequestMapping("/login")
@Api(tags = "登录")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 刷新Token
     *
     * @param refreshToken
     * @return
     * @throws Exception
     */
    @ApiOperation("刷新Token")
    @PostMapping("/refreshToken")
    public ResultDataVO<LoginVO> refreshToken(
            @ApiParam(name = "refreshToken", value = "刷新token", required = true) @RequestBody String refreshToken
    ) throws Exception {
        return ResultDataVO.success(loginService.refreshToken(refreshToken, "com/example/api"), "刷新成功");
    }
}
