package com.example.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 空间表

 * @TableName sp_space
 */
@TableName(value ="sp_space")
@Data
public class Space implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer spaceId;

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 空间唯一识别码
     */
    private String spaceCode;

    /**
     * 封面
     */
    private String spaceThumb;

    /**
     * 1-显示 0-隐藏
     */
    private Integer isShow;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 软删
     */
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
        Space other = (Space) that;
        return (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
            && (this.getSpaceName() == null ? other.getSpaceName() == null : this.getSpaceName().equals(other.getSpaceName()))
            && (this.getSpaceCode() == null ? other.getSpaceCode() == null : this.getSpaceCode().equals(other.getSpaceCode()))
            && (this.getSpaceThumb() == null ? other.getSpaceThumb() == null : this.getSpaceThumb().equals(other.getSpaceThumb()))
            && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpaceId() == null) ? 0 : getSpaceId().hashCode());
        result = prime * result + ((getSpaceName() == null) ? 0 : getSpaceName().hashCode());
        result = prime * result + ((getSpaceCode() == null) ? 0 : getSpaceCode().hashCode());
        result = prime * result + ((getSpaceThumb() == null) ? 0 : getSpaceThumb().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spaceId=").append(spaceId);
        sb.append(", spaceName=").append(spaceName);
        sb.append(", spaceCode=").append(spaceCode);
        sb.append(", spaceThumb=").append(spaceThumb);
        sb.append(", isShow=").append(isShow);
        sb.append(", sort=").append(sort);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}