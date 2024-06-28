package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordDTO {
    @ApiModelProperty(value = "旧密码", required = true, example = "******")
    @NotNull(message = "缺少旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "修改密码", required = true, example = "******")
    @NotNull(message = "请输入修改密码")
    private String password;

    @ApiModelProperty(value = "确认密码", required = true, example = "******")
    @NotNull(message = "请输入确认密码")
    private String confirmPassword;
}
