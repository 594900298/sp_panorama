package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 购物车表
 *
 * @TableName sp_car
 */
@TableName(value = "sp_car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    @ApiModelProperty(value = "购物车id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer carId;

    @ApiModelProperty(value = "userId", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "商品sku", example = "6d7a31a8-1b38-4b9e-bbc6-2b882f81071f")
    private String skuCode;

    @ApiModelProperty(value = "商品数量", example = "1")
    private Integer quantity;

    @ApiModelProperty(value = "选中状态", example = "true")
    private Boolean checked;

    @ApiModelProperty(value = "更新时间", example = "1714722989")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

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
        Car other = (Car) that;
        return (this.getCarId() == null ? other.getCarId() == null : this.getCarId().equals(other.getCarId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
                && (this.getSkuCode() == null ? other.getSkuCode() == null : this.getSkuCode().equals(other.getSkuCode()))
                && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
                && (this.getChecked() == null ? other.getChecked() == null : this.getChecked().equals(other.getChecked()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCarId() == null) ? 0 : getCarId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getSkuCode() == null) ? 0 : getSkuCode().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getChecked() == null) ? 0 : getChecked().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", carId=").append(carId);
        sb.append(", userId=").append(userId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", skuCode=").append(skuCode);
        sb.append(", quantity=").append(quantity);
        sb.append(", checked=").append(checked);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}