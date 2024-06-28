package com.example.admin.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailAttrValuePO {
    @ApiModelProperty(value = "属性值id", example = "1")
    private Integer attrValueId;

    @ApiModelProperty(value = "属性值名称", example = "红色")
    private String attrValueName;

    @ApiModelProperty(value = "属性图片", example = "url")
    private String attrValueImage;
}