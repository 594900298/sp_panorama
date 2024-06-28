package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 添加管理员DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddDTO {

    @ApiModelProperty(value = "账号", required = true, example = "admin")
    @NotNull(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "******")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账号状态 1-正常 0-禁用")
    private Boolean status;

}
