package com.example.admin.controller;

import com.example.admin.dto.LoginDTO;
import com.example.admin.service.LoginService;
import com.example.admin.vo.LoginVO;
import com.example.common.vo.ResultDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     *
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/code")
    public ResultDataVO<LoginVO> getCaptcha(
            @Validated @RequestBody LoginDTO loginDTO
    ) throws Exception {
        return ResultDataVO.success(loginService.login(loginDTO), "登录成功");
    }

    /**
     * 刷新Token
     *
     * @return
     * @throws Exception
     */
    @ApiOperation("刷新Token")
    @PostMapping("/refreshToken")
    public ResultDataVO<LoginVO> refreshToken(
            @ApiParam(name = "refreshToken",value = "刷新token",required = true) @RequestBody String refreshToken
    ){
        return ResultDataVO.success(loginService.refreshToken(refreshToken,"admin"), "刷新成功");
    }

}
