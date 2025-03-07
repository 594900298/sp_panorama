package com.example.admin.controller;

import com.example.common.vo.ResultDataVO;
import com.example.common.utils.CaptchaUtil;
import com.example.common.vo.CaptchaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码
 */
@RestController
@RequestMapping("/captcha")
@Api(tags = "验证码")
public class CaptchaController {
    @Autowired
    private CaptchaUtil captchaUtil;

    /**
     * 获取验证码
     *
     * @param codeSign
     * @return
     */
    @ApiOperation("获取验证码")
    @GetMapping("/getCaptcha")
    public ResultDataVO<CaptchaVO> getCaptcha(
            @ApiParam(name = "codeSign", value = "验证码签名", required = true, example = "初次获取可为空") @RequestParam(value = "codeSign") String codeSign) {
        return ResultDataVO.success(captchaUtil.getCaptcha(codeSign));
    }
}
