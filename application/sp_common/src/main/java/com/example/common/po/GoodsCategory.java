package com.example.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.Interface.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品分类表
 *
 * @TableName sp_goods_category
 */
@TableName(value = "sp_goods_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCategory implements Serializable, TreeNode {
    @ApiModelProperty(value = "分类id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer catId;

    @ApiModelProperty(value = "上级id", example = "1")
    private Integer pid;

    @ApiModelProperty(value = "分类名称", example = "半夏")
    private String catName;

    @ApiModelProperty(value = "分类等级", example = "1")
    private Integer catLevel;

    @ApiModelProperty(value = "分类全路径", example = ",1,")
    private String catPath;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "是否展示", example = "Boolean")
    private Boolean isShow;

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
        GoodsCategory other = (GoodsCategory) that;
        return (this.getCatId() == null ? other.getCatId() == null : this.getCatId().equals(other.getCatId()))
                && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
                && (this.getCatName() == null ? other.getCatName() == null : this.getCatName().equals(other.getCatName()))
                && (this.getCatLevel() == null ? other.getCatLevel() == null : this.getCatLevel().equals(other.getCatLevel()))
                && (this.getCatPath() == null ? other.getCatPath() == null : this.getCatPath().equals(other.getCatPath()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCatId() == null) ? 0 : getCatId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getCatName() == null) ? 0 : getCatName().hashCode());
        result = prime * result + ((getCatLevel() == null) ? 0 : getCatLevel().hashCode());
        result = prime * result + ((getCatPath() == null) ? 0 : getCatPath().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", catId=").append(catId);
        sb.append(", catPid=").append(pid);
        sb.append(", catName=").append(catName);
        sb.append(", catLevel=").append(catLevel);
        sb.append(", catPath=").append(catPath);
        sb.append(", sort=").append(sort);
        sb.append(", isShow=").append(isShow);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String getId() {
        return String.valueOf(getCatId());
    }

    @Override
    public String getParentId() {
        return String.valueOf(getPid());
    }

    @Override
    public String getWeight() {
        return String.valueOf(getSort());
    }

    @Override
    public String getName() {
        return getCatName();
    }
}