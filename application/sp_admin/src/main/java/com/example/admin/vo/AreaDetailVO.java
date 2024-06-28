package com.example.admin.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDetailVO {
    @ApiModelProperty(value = "地区id", example = "1")
    private Integer areaId;

    @ApiModelProperty(value = "上级id", example = "0")
    private Integer pid;

    @ApiModelProperty(value = "地区名称", example = "北京市")
    private String areaName;

    @ApiModelProperty(value = "地区首字母", example = "A")
    private String areaKey;

    @ApiModelProperty(value = "级别标志  0-省 1-市 2-县区", example = "0")
    private Short areaType;

}
