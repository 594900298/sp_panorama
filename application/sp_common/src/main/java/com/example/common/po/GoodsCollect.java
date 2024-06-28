package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 商品收藏表
 * @TableName sp_goods_collect
 */
@TableName(value ="sp_goods_collect")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCollect implements Serializable {
    @ApiModelProperty(value = "收藏id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer collectId;

    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "商品id", example = "1")
    private Integer goodsId;

    @ApiModelProperty(value = "更新时间", example = "1714722989")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

    @ApiModelProperty(value = "创建时间", example = "1714722989")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer createTime;

    /**
     * 删除时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteTime;

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
        GoodsCollect other = (GoodsCollect) that;
        return (this.getCollectId() == null ? other.getCollectId() == null : this.getCollectId().equals(other.getCollectId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCollectId() == null) ? 0 : getCollectId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", collectId=").append(collectId);
        sb.append(", userId=").append(userId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}