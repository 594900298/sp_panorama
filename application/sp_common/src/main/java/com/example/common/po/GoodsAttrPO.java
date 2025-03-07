package com.example.common.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品属性表
 * @TableName sp_goods_attr
 */
@TableName(value ="sp_goods_attr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAttrPO implements Serializable {
    @ApiModelProperty(value = "属性id", example = "1")
    @TableId
    private Integer attrId;

    @ApiModelProperty(value = "绑定商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "属性名称", example = "半夏")
    private String attrName;

    @ApiModelProperty(value = "是否设置规格图片", example = "Boolean")
    private Boolean setImage;

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
        GoodsAttrPO other = (GoodsAttrPO) that;
        return (this.getAttrId() == null ? other.getAttrId() == null : this.getAttrId().equals(other.getAttrId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getAttrName() == null ? other.getAttrName() == null : this.getAttrName().equals(other.getAttrName()))
            && (this.getSetImage() == null ? other.getSetImage() == null : this.getSetImage().equals(other.getSetImage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAttrId() == null) ? 0 : getAttrId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getAttrName() == null) ? 0 : getAttrName().hashCode());
        result = prime * result + ((getSetImage() == null) ? 0 : getSetImage().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attrId=").append(attrId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", attrName=").append(attrName);
        sb.append(", setImage=").append(setImage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}