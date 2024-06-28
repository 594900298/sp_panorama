package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 编辑管理员密码DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEditPasswordDTO {
    @ApiModelProperty(value = "用户id", required = true, example = "1")
    @NotNull(message = "缺少主键")
    private Integer userId;

    @ApiModelProperty(value = "密码", required = true, example = "******")
    @NotNull(message = "密码不能为空")
    private String password;
}
