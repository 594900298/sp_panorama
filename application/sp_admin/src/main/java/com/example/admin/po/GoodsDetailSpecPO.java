package com.example.admin.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品规格
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailSpecPO {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "组合名称", example = "[\"颜色:银色\",\"内存:512G\"]")
    private String keyName;

    @ApiModelProperty(value = "组合图片", example = "url")
    private String cover;

    @ApiModelProperty(value = "轮播图片", example = "url")
    private String gallery;

    @ApiModelProperty(value = "详细介绍", example = "")
    private String content;

    @ApiModelProperty(value = "折扣价", example = "10")
    private BigDecimal oldPrice;

    @ApiModelProperty(value = "商品价格", example = "10")
    private BigDecimal price;

    @ApiModelProperty(value = "库存数量", example = "1")
    private Integer storeCount;

    @ApiModelProperty(value = "商品码", example = "66e115da-5766-4199-a032-a152ae88d1c3")
    private String skuCode;

    @ApiModelProperty(value = "条形码", example = "152ae88d1c3")
    private String barCode;

    @ApiModelProperty(value = "是否上架 0未上架 1已上架", example = "ture")
    private Boolean status;
}