package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    @ApiModelProperty(value = "过期时间", example = "1714123400")
    private Long expire;

    @ApiModelProperty(value = "令牌", example = "eyJhb...")
    private String token;

    @ApiModelProperty(value = "刷新令牌", example = "eyJhb...")
    private String refreshToken;

    @ApiModelProperty(value = "用户名", example = "超级管理员")
    private String username;
}
