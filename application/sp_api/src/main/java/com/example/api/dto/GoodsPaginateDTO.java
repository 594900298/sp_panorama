package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPaginateDTO {
    @ApiModelProperty(value = "分类id", example = "1")
    private Integer catId;

    @ApiModelProperty(value = "商品名称", example = "半夏")
    private String goodsName;

    @ApiModelProperty(value = "是否热门", example = "true")
    private Boolean isHot;

    @ApiModelProperty(value = "是否新品", example = "true")
    private Boolean isNew;
}
