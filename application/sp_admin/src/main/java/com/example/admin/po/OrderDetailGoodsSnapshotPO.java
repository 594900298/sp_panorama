package com.example.admin.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailGoodsSnapshotPO {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称", example = "手机")
    private String goodsName;

    @ApiModelProperty(value = "商品画册", example = "")
    private String goodsGallery;

    @ApiModelProperty(value = "商品详情", example = "")
    private String goodsContent;

    @ApiModelProperty(value = "组合名称", example = "[\"颜色:黑色\",\"内存:512G\"]")
    private String keyName;

    @ApiModelProperty(value = "封面", example = "url")
    private String cover;

    @ApiModelProperty(value = "封面", example = "")
    private String gallery;

    @ApiModelProperty(value = "商品详情", example = "")
    private String content;

    @ApiModelProperty(value = "原价", example = "10")
    private BigDecimal oldPrice;

    @ApiModelProperty(value = "当前价格", example = "10")
    private BigDecimal price;

    @ApiModelProperty(value = "商品码", example = "10")
    private String skuCode;

    @ApiModelProperty(value = "条形码", example = "e7bf5be7-ddf0-4d13-bdb5-44c4695b47ba")
    private String barCode;

    @ApiModelProperty(value = "商品数量", example = "44c4695b47ba")
    private Integer quantity;
}
