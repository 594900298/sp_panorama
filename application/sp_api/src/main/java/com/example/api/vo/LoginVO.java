package com.example.api.vo;

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

    @ApiModelProperty(value = "用户名", example = "半夏")
    private String name;

    @ApiModelProperty(value = "openid", example = "oPj7W5TJFzG90mDKuqSyHe5e7gow")
    private String openid;
}
