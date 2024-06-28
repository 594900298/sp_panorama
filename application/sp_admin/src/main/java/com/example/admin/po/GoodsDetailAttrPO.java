package com.example.admin.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 属性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailAttrPO {
    @ApiModelProperty(value = "属性id", example = "1")
    private Integer attrId;

    @ApiModelProperty(value = "属性名称", example = "颜色")
    private String attrName;

    @ApiModelProperty(value = "是否展示封面", example = "true")
    private Boolean setImage;

    @ApiModelProperty(value = "属性值", example = "属性值数组")
    private List<GoodsDetailAttrValuePO> attrValues;
}
