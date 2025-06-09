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
public class RefreshTokenDTO {
    @ApiModelProperty(value = "刷新token", required = true, example = "token")
    @NotNull(message = "刷新token不能为空")
    private String refreshToken;
}
