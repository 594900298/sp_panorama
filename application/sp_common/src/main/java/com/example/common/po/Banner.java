package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

;

/**
 * 轮播
 *
 * @TableName sp_banner
 */
@TableName(value = "sp_banner")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {
    @ApiModelProperty(value = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer bannerId;

    @ApiModelProperty(value = "轮播图名称", example = "半夏")
    private String bannerName;

    @ApiModelProperty(value = "传轮播图", example = "url")
    private String bannerImage;

    @ApiModelProperty(value = "轮播链接", example = "url")
    private String bannerLink;

    @ApiModelProperty(value = "是否显示", example = "ture")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

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
        Banner other = (Banner) that;
        return (this.getBannerId() == null ? other.getBannerId() == null : this.getBannerId().equals(other.getBannerId()))
                && (this.getBannerName() == null ? other.getBannerName() == null : this.getBannerName().equals(other.getBannerName()))
                && (this.getBannerImage() == null ? other.getBannerImage() == null : this.getBannerImage().equals(other.getBannerImage()))
                && (this.getBannerLink() == null ? other.getBannerLink() == null : this.getBannerLink().equals(other.getBannerLink()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBannerId() == null) ? 0 : getBannerId().hashCode());
        result = prime * result + ((getBannerName() == null) ? 0 : getBannerName().hashCode());
        result = prime * result + ((getBannerImage() == null) ? 0 : getBannerImage().hashCode());
        result = prime * result + ((getBannerLink() == null) ? 0 : getBannerLink().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bannerId=").append(bannerId);
        sb.append(", bannerName=").append(bannerName);
        sb.append(", bannerImage=").append(bannerImage);
        sb.append(", bannerLink=").append(bannerLink);
        sb.append(", isShow=").append(isShow);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}