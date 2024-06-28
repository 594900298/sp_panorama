package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetailVO {
    @ApiModelProperty(value = "角色id", example = "1")
    private Integer roleId;

    @ApiModelProperty(value = "角色名称", example = "")
    private String roleName;

    @ApiModelProperty(value = "描述", example = "")
    private String roleDesc;

    @ApiModelProperty(value = "权限列表", example = "name")
    private List<Integer> rules;
}
