package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 编辑管理员DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEditDTO {
    @ApiModelProperty(value = "用户id", required = true, example = "1")
    @NotNull(message = "缺少主键")
    private Integer userId;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 1-正常 0-禁用
     */
    @ApiModelProperty(value = "账号状态 1-正常 0-禁用")
    private Boolean status;

}
