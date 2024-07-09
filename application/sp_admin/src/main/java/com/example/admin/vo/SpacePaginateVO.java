package com.example.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 空间表
 *
 * @TableName sp_space
 */
@TableName(value = "sp_space")
@Data
public class SpacePaginateVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "空间id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer spaceId;

    /**
     * 空间名称
     */
    @ApiModelProperty(value = "空间名称", example = "故宫")
    private String spaceName;

    /**
     * 空间唯一识别码
     */
    @ApiModelProperty(value = "空间唯一识别码", example = "自动生成")
    private String spaceCode;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面", example = "url")
    private String spaceThumb;

    /**
     * 1-显示 0-隐藏
     */
    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "50")
    private Integer sort;

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
        SpacePaginateVO other = (SpacePaginateVO) that;
        return (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
                && (this.getSpaceName() == null ? other.getSpaceName() == null : this.getSpaceName().equals(other.getSpaceName()))
                && (this.getSpaceCode() == null ? other.getSpaceCode() == null : this.getSpaceCode().equals(other.getSpaceCode()))
                && (this.getSpaceThumb() == null ? other.getSpaceThumb() == null : this.getSpaceThumb().equals(other.getSpaceThumb()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()));
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}