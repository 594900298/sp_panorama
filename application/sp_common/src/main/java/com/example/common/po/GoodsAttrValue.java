package com.example.common.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品属值性表
 *
 * @TableName sp_goods_attr_value
 */
@TableName(value = "sp_goods_attr_value")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAttrValue implements Serializable {
    @ApiModelProperty(value = "属性值id", example = "1")
    @TableId
    private Integer attrValueId;

    @ApiModelProperty(value = "商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "属性id", example = "1")
    private Integer attrId;

    @ApiModelProperty(value = "属性值名称", example = "半夏")
    private String attrValueName;

    @ApiModelProperty(value = "属性值图片", example = "url")
    private String attrValueImage;

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
        GoodsAttrValue other = (GoodsAttrValue) that;
        return (this.getAttrValueId() == null ? other.getAttrValueId() == null : this.getAttrValueId().equals(other.getAttrValueId()))
                && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
                && (this.getAttrId() == null ? other.getAttrId() == null : this.getAttrId().equals(other.getAttrId()))
                && (this.getAttrValueName() == null ? other.getAttrValueName() == null : this.getAttrValueName().equals(other.getAttrValueName()))
                && (this.getAttrValueImage() == null ? other.getAttrValueImage() == null : this.getAttrValueImage().equals(other.getAttrValueImage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAttrValueId() == null) ? 0 : getAttrValueId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getAttrId() == null) ? 0 : getAttrId().hashCode());
        result = prime * result + ((getAttrValueName() == null) ? 0 : getAttrValueName().hashCode());
        result = prime * result + ((getAttrValueImage() == null) ? 0 : getAttrValueImage().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attrValueId=").append(attrValueId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", attrId=").append(attrId);
        sb.append(", attrValueName=").append(attrValueName);
        sb.append(", attrValueImage=").append(attrValueImage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}