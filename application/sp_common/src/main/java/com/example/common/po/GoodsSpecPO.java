package com.example.common.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品规格表
 * @TableName sp_goods_spec
 */
@TableName(value ="sp_goods_spec")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSpecPO implements Serializable {
    @ApiModelProperty(value = "主键", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "组合名称", example = "半夏")
    private String keyName;

    @ApiModelProperty(value = "组合图片", example = "url")
    private String cover;

    @ApiModelProperty(value = "轮播图片", example = "url")
    private String gallery;

    @ApiModelProperty(value = "内容", example = "半夏")
    private String content;

    @ApiModelProperty(value = "折扣价", example = "10.00")
    private BigDecimal oldPrice;

    @ApiModelProperty(value = "商品价格", example = "10.00")
    private BigDecimal price;

    @ApiModelProperty(value = "库存数量", example = "10")
    private Integer storeCount;

    @ApiModelProperty(value = "商品码", example = "6d7a31a8-1b38-4b9e-bbc6-2b882f81071f")
    private String skuCode;

    @ApiModelProperty(value = "商品条形码", example = "2b882f81071f")
    private String barCode;

    @ApiModelProperty(value = "是否上架", example = "true")
    private Boolean status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public GoodsSpecPO(Integer goodsId, String keyName, String cover, BigDecimal oldPrice, BigDecimal price, Integer storeCount, String skuCode, String barCode) {
        this.goodsId = goodsId;
        this.keyName = keyName;
        this.cover = cover;
        this.oldPrice = oldPrice;
        this.price = price;
        this.storeCount = storeCount;
        this.skuCode = skuCode;
        this.barCode = barCode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        GoodsSpecPO other = (GoodsSpecPO) that;
        return (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getKeyName() == null ? other.getKeyName() == null : this.getKeyName().equals(other.getKeyName()))
            && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
            && (this.getGallery() == null ? other.getGallery() == null : this.getGallery().equals(other.getGallery()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getOldPrice() == null ? other.getOldPrice() == null : this.getOldPrice().equals(other.getOldPrice()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getStoreCount() == null ? other.getStoreCount() == null : this.getStoreCount().equals(other.getStoreCount()))
            && (this.getSkuCode() == null ? other.getSkuCode() == null : this.getSkuCode().equals(other.getSkuCode()))
            && (this.getBarCode() == null ? other.getBarCode() == null : this.getBarCode().equals(other.getBarCode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getKeyName() == null) ? 0 : getKeyName().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getGallery() == null) ? 0 : getGallery().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getOldPrice() == null) ? 0 : getOldPrice().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getStoreCount() == null) ? 0 : getStoreCount().hashCode());
        result = prime * result + ((getSkuCode() == null) ? 0 : getSkuCode().hashCode());
        result = prime * result + ((getBarCode() == null) ? 0 : getBarCode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", keyName=").append(keyName);
        sb.append(", cover=").append(cover);
        sb.append(", gallery=").append(gallery);
        sb.append(", content=").append(content);
        sb.append(", oldPrice=").append(oldPrice);
        sb.append(", price=").append(price);
        sb.append(", storeCount=").append(storeCount);
        sb.append(", skuCode=").append(skuCode);
        sb.append(", barCode=").append(barCode);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}