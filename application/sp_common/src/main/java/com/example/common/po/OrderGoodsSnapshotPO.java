package com.example.common.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单关联商品快照
 *
 * @TableName sp_order_goods_snapshot
 */
@TableName(value = "sp_order_goods_snapshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsSnapshotPO implements Serializable {
    @ApiModelProperty(value = "主键", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "主键", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "主键", example = "1")
    private Integer orderId;

    @ApiModelProperty(value = "主键", example = "1")
    private String goodsName;

    @ApiModelProperty(value = "商品轮播图", example = "ur")
    private String goodsGallery;
    @ApiModelProperty(value = "主键", example = "1")
    private String keyName;

    @ApiModelProperty(value = "组合图片", example = "url")
    private String cover;

    @ApiModelProperty(value = "规格轮播图", example = "ur")
    private String gallery;
    @ApiModelProperty(value = "内容", example = "半夏")
    private String content;

    @ApiModelProperty(value = "折扣价", example = "10.00")
    private BigDecimal oldPrice;

    @ApiModelProperty(value = "商品价格", example = "10.00")
    private BigDecimal price;

    @ApiModelProperty(value = "商品码", example = "710d14eb-ec38-49cd-98fb-af7b3284fb31")
    private String skuCode;

    @ApiModelProperty(value = "商品条形码", example = "98fb-af7b3284fb31")
    private String barCode;

    @ApiModelProperty(value = "商品数量", example = "1")
    private Integer quantity;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        OrderGoodsSnapshotPO other = (OrderGoodsSnapshotPO) that;
        return (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
                && (this.getKeyName() == null ? other.getKeyName() == null : this.getKeyName().equals(other.getKeyName()))
                && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
                && (this.getGallery() == null ? other.getGallery() == null : this.getGallery().equals(other.getGallery()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getOldPrice() == null ? other.getOldPrice() == null : this.getOldPrice().equals(other.getOldPrice()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getSkuCode() == null ? other.getSkuCode() == null : this.getSkuCode().equals(other.getSkuCode()))
                && (this.getBarCode() == null ? other.getBarCode() == null : this.getBarCode().equals(other.getBarCode()))
                && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getKeyName() == null) ? 0 : getKeyName().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getGallery() == null) ? 0 : getGallery().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getOldPrice() == null) ? 0 : getOldPrice().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getSkuCode() == null) ? 0 : getSkuCode().hashCode());
        result = prime * result + ((getBarCode() == null) ? 0 : getBarCode().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", keyName=").append(keyName);
        sb.append(", cover=").append(cover);
        sb.append(", gallery=").append(gallery);
        sb.append(", content=").append(content);
        sb.append(", oldPrice=").append(oldPrice);
        sb.append(", price=").append(price);
        sb.append(", skuCode=").append(skuCode);
        sb.append(", barCode=").append(barCode);
        sb.append(", quantity=").append(quantity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}