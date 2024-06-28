package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePaginateVO {
    @ApiModelProperty(value = "角色id", example = "1")
    private Integer roleId;

    @ApiModelProperty(value = "角色名称", example = "超级管理员")
    private String roleName;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "是否系统提供", example = "true")
    private Boolean isSystem;
}
