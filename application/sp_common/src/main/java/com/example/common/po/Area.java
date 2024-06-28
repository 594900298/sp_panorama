package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.Interface.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 区域表
 *
 * @TableName sp_area
 */
@TableName(value = "sp_area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area implements Serializable, TreeNode {
    @ApiModelProperty(value = "地区id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer areaId;

    @ApiModelProperty(value = "父ID", example = "1")
    private Integer pid;

    @ApiModelProperty(value = "地区名称", example = "上海市")
    private String areaName;

    @ApiModelProperty(value = "是否显示", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "地区首字母", example = "S")
    private String areaKey;

    @ApiModelProperty(value = "级别标志", example = "0-省 1-市 2-县区")
    private Short areaType;

    @ApiModelProperty(value = "创建时间", example = "1712046091")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

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
        Area other = (Area) that;
        return (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
                && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
                && (this.getAreaName() == null ? other.getAreaName() == null : this.getAreaName().equals(other.getAreaName()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getAreaKey() == null ? other.getAreaKey() == null : this.getAreaKey().equals(other.getAreaKey()))
                && (this.getAreaType() == null ? other.getAreaType() == null : this.getAreaType().equals(other.getAreaType()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getAreaName() == null) ? 0 : getAreaName().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getAreaKey() == null) ? 0 : getAreaKey().hashCode());
        result = prime * result + ((getAreaType() == null) ? 0 : getAreaType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", areaId=").append(areaId);
        sb.append(", parentId=").append(pid);
        sb.append(", areaName=").append(areaName);
        sb.append(", isShow=").append(isShow);
        sb.append(", sort=").append(sort);
        sb.append(", areaKey=").append(areaKey);
        sb.append(", areaType=").append(areaType);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String getId() {
        return String.valueOf(getAreaId());
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
        return getAreaName();
    }
}