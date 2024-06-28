package com.example.admin.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailPO {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "分类id", example = "1")
    private Integer catId;

    @ApiModelProperty(value = "商品名称", example = "半夏商城")
    private String goodsName;

    @ApiModelProperty(value = "关键词", example = "")
    private String goodsKeyword;

    @ApiModelProperty(value = "简介", example = "")
    private String goodsDesc;

    @ApiModelProperty(value = "商品封面", example = "url")
    private String goodsCover;

    @ApiModelProperty(value = "画册", example = "url")
    private String goodsGallery;

    @ApiModelProperty(value = "详细描述", example = "")
    private String goodsContent;

    @ApiModelProperty(value = "单位", example = "台")
    private String unitName;

    @ApiModelProperty(value = "是否多规格 0单规格 1多规格", example = "true")
    private Boolean specType;

    @ApiModelProperty(value = "是否展示", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "属性", example = "")
    private List<GoodsDetailAttrPO> attr;

    @ApiModelProperty(value = "规格组合", example = "")
    private List<GoodsDetailSpecPO> spec;
}
