package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 添加区域DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaAddDTO {
    @ApiModelProperty(value = "上级id", required = true, example = "顶级传0")
    @NotNull(message = "上级id不能为空")
    private Integer pid;

    @ApiModelProperty(value = "地区名称", required = true, example = "阿姆斯特丹")
    @NotNull(message = "地区名称不能为空")
    private String areaName;

    @ApiModelProperty(value = "地区首字母", required = true, example = "A")
    @NotNull(message = "地区首字母不能为空")
    private String areaKey;

    @ApiModelProperty(value = "级别标志  0-省 1-市 2-县区", required = true, example = "0")
    @NotNull(message = "地区首字母不能为空")
    private Short areaType;
}
