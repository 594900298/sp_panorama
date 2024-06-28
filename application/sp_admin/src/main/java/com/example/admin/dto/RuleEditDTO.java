package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 修改规则DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEditDTO {
    @ApiModelProperty(value = "规则id", required = true, example = "1")
    @NotNull(message = "主键不能为空")
    private Integer ruleId;

    @ApiModelProperty(value = "路由名称", required = true, example = "轮播图添加")
    @NotNull(message = "路由名称不能为空")
    private String name;

    @ApiModelProperty(value = "规则地址", required = true, example = "Banner/add")
    @NotNull(message = "规则地址不能为空")
    private String ruleUrl;

    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 999, message = "排序只能填写0-999")
    private Integer sort;

    @ApiModelProperty(value = "是否为菜单", required = true, example = "true")
    @NotNull(message = "是否为菜单不能为空")
    private Boolean isMenu;
}
