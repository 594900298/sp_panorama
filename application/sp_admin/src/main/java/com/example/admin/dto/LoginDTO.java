package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 登录DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotNull(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "*******")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码签名", required = true, example = "验证码接口获取")
    @NotNull(message = "参数异常")
    private String codeSign;

    @ApiModelProperty(value = "验证码", required = true, example = "1234")
    @NotNull(message = "参数异常")
    private String code;

}
