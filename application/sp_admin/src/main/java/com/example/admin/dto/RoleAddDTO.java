package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加管理员DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAddDTO {
    @ApiModelProperty(value = "角色名称",required = true, example = "")
    @NotNull(message = "角色名称不能空")
    private String roleName;

    @ApiModelProperty(value = "描述", example = "角色具有的权限较高")
    private String roleDesc;

    @ApiModelProperty(value = "请选择权限", example = "[1,2,3]")
    private List<Integer> rules;
}
