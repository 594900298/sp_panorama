package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleDetailVO {
    @ApiModelProperty(value = "规则id", example = "1")
    private Integer ruleId;

    @ApiModelProperty(value = "上级id", example = "0")
    private Integer pid;

    @ApiModelProperty(value = "规则名称", example = "name")
    private String name;

    @ApiModelProperty(value = "接口url", example = "name")
    private String ruleUrl;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "是否为菜单", example = "Boolean")
    private Boolean isMenu;
}
