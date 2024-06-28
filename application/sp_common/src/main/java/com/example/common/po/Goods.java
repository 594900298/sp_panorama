package com.example.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品表
 *
 * @TableName sp_goods
 */
@TableName(value = "sp_goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {
    @ApiModelProperty(value = "商品id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer goodsId;

    @ApiModelProperty(value = "分类id", example = "1")
    private Integer catId;

    @ApiModelProperty(value = "商品名称", example = "半夏")
    private String goodsName;

    @ApiModelProperty(value = "商品关键词", example = "半夏")
    private String goodsKeyword;

    @ApiModelProperty(value = "商品描述", example = "半夏")
    private String goodsDesc;

    @ApiModelProperty(value = "商品封面", example = "url")
    private String goodsCover;

    @ApiModelProperty(value = "商品图片画册", example = "url")
    private String goodsGallery;

    @ApiModelProperty(value = "商品详情", example = "半夏")
    private String goodsContent;

    @ApiModelProperty(value = "商品计量单位名称", example = "只")
    private String unitName;

    @ApiModelProperty(value = "是否多规格", example = "ture")
    private Boolean specType;

    @ApiModelProperty(value = "是否热门", example = "ture")
    private Boolean isHot;

    @ApiModelProperty(value = "是否新品", example = "ture")
    private Boolean isNew;

    @ApiModelProperty(value = "是否展示", example = "ture")
    private Boolean isShow;

    @ApiModelProperty(value = "商品排序", example = "0")
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Goods(Integer catId, String goodsName, String goodsKeyword, String goodsDesc, String goodsCover, String goodsGallery, String goodsContent, String unitName, Boolean specType, Boolean isHot, Boolean isNew, Boolean isShow) {
        this.catId = catId;
        this.goodsName = goodsName;
        this.goodsKeyword = goodsKeyword;
        this.goodsDesc = goodsDesc;
        this.goodsCover = goodsCover;
        this.goodsGallery = goodsGallery;
        this.goodsContent = goodsContent;
        this.unitName = unitName;
        this.specType = specType;
        this.isHot = isHot;
        this.isNew = isNew;
        this.isShow = isShow;
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
        Goods other = (Goods) that;
        return (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
                && (this.getCatId() == null ? other.getCatId() == null : this.getCatId().equals(other.getCatId()))
                && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
                && (this.getGoodsKeyword() == null ? other.getGoodsKeyword() == null : this.getGoodsKeyword().equals(other.getGoodsKeyword()))
                && (this.getGoodsDesc() == null ? other.getGoodsDesc() == null : this.getGoodsDesc().equals(other.getGoodsDesc()))
                && (this.getGoodsCover() == null ? other.getGoodsCover() == null : this.getGoodsCover().equals(other.getGoodsCover()))
                && (this.getGoodsGallery() == null ? other.getGoodsGallery() == null : this.getGoodsGallery().equals(other.getGoodsGallery()))
                && (this.getGoodsContent() == null ? other.getGoodsContent() == null : this.getGoodsContent().equals(other.getGoodsContent()))
                && (this.getUnitName() == null ? other.getUnitName() == null : this.getUnitName().equals(other.getUnitName()))
                && (this.getSpecType() == null ? other.getSpecType() == null : this.getSpecType().equals(other.getSpecType()))
                && (this.getIsHot() == null ? other.getIsHot() == null : this.getIsHot().equals(other.getIsHot()))
                && (this.getIsNew() == null ? other.getIsNew() == null : this.getIsNew().equals(other.getIsNew()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getCatId() == null) ? 0 : getCatId().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getGoodsKeyword() == null) ? 0 : getGoodsKeyword().hashCode());
        result = prime * result + ((getGoodsDesc() == null) ? 0 : getGoodsDesc().hashCode());
        result = prime * result + ((getGoodsCover() == null) ? 0 : getGoodsCover().hashCode());
        result = prime * result + ((getGoodsGallery() == null) ? 0 : getGoodsGallery().hashCode());
        result = prime * result + ((getGoodsContent() == null) ? 0 : getGoodsContent().hashCode());
        result = prime * result + ((getUnitName() == null) ? 0 : getUnitName().hashCode());
        result = prime * result + ((getSpecType() == null) ? 0 : getSpecType().hashCode());
        result = prime * result + ((getIsHot() == null) ? 0 : getIsHot().hashCode());
        result = prime * result + ((getIsNew() == null) ? 0 : getIsNew().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", catId=").append(catId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsKeyword=").append(goodsKeyword);
        sb.append(", goodsDesc=").append(goodsDesc);
        sb.append(", goodsCover=").append(goodsCover);
        sb.append(", goodsGallery=").append(goodsGallery);
        sb.append(", goodsContent=").append(goodsContent);
        sb.append(", unitName=").append(unitName);
        sb.append(", specType=").append(specType);
        sb.append(", isHot=").append(isHot);
        sb.append(", isNew=").append(isNew);
        sb.append(", isShow=").append(isShow);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}