package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.HotspotStyle;
import com.example.common.enums.HotspotType;
import lombok.Data;

import java.io.Serializable;

/**
 * 热点表
 *
 * @TableName sp_hotspot
 */
@TableName(value = "sp_hotspot")
@Data
public class HotspotPO implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer hotspotId;

    /**
     * 场景id
     */
    private Integer sceneId;

    /**
     * 热点名称
     */
    private String hotspotName;

    /**
     * 随机生成的字符串
     */
    private String randomString;

    /**
     * 热点样式
     */
    private HotspotStyle hotspotStyle;

    /**
     * CHANGE_SCENE切换场景热点 PRODUCT_HOTSPOT货架热点 VIDEO_HOTSPOT视频热点
     */
    private HotspotType hotspotType;

    /**
     * 关联值
     */
    private String hotspotValue;

    /**
     * 全景中球体坐标的水平位置
     */
    private String ath;

    /**
     * 全景中球体坐标的垂直位置
     */
    private String atv;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 宽度
     */
    private Integer width;

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
        HotspotPO other = (HotspotPO) that;
        return (this.getHotspotId() == null ? other.getHotspotId() == null : this.getHotspotId().equals(other.getHotspotId()))
                && (this.getSceneId() == null ? other.getSceneId() == null : this.getSceneId().equals(other.getSceneId()))
                && (this.getHotspotName() == null ? other.getHotspotName() == null : this.getHotspotName().equals(other.getHotspotName()))
                && (this.getRandomString() == null ? other.getRandomString() == null : this.getRandomString().equals(other.getRandomString()))
                && (this.getHotspotStyle() == null ? other.getHotspotStyle() == null : this.getHotspotStyle().equals(other.getHotspotStyle()))
                && (this.getHotspotType() == null ? other.getHotspotType() == null : this.getHotspotType().equals(other.getHotspotType()))
                && (this.getHotspotValue() == null ? other.getHotspotValue() == null : this.getHotspotValue().equals(other.getHotspotValue()))
                && (this.getAth() == null ? other.getAth() == null : this.getAth().equals(other.getAth()))
                && (this.getAtv() == null ? other.getAtv() == null : this.getAtv().equals(other.getAtv()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
                && (this.getWidth() == null ? other.getWidth() == null : this.getWidth().equals(other.getWidth()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHotspotId() == null) ? 0 : getHotspotId().hashCode());
        result = prime * result + ((getSceneId() == null) ? 0 : getSceneId().hashCode());
        result = prime * result + ((getHotspotName() == null) ? 0 : getHotspotName().hashCode());
        result = prime * result + ((getRandomString() == null) ? 0 : getRandomString().hashCode());
        result = prime * result + ((getHotspotStyle() == null) ? 0 : getHotspotStyle().hashCode());
        result = prime * result + ((getHotspotType() == null) ? 0 : getHotspotType().hashCode());
        result = prime * result + ((getHotspotValue() == null) ? 0 : getHotspotValue().hashCode());
        result = prime * result + ((getAth() == null) ? 0 : getAth().hashCode());
        result = prime * result + ((getAtv() == null) ? 0 : getAtv().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getWidth() == null) ? 0 : getWidth().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hotspotId=").append(hotspotId);
        sb.append(", sceneId=").append(sceneId);
        sb.append(", hotspotName=").append(hotspotName);
        sb.append(", randomString=").append(randomString);
        sb.append(", hotspotStyle=").append(hotspotStyle);
        sb.append(", hotspotType=").append(hotspotType);
        sb.append(", hotspotValue=").append(hotspotValue);
        sb.append(", ath=").append(ath);
        sb.append(", atv=").append(atv);
        sb.append(", createTime=").append(createTime);
        sb.append(", height=").append(height);
        sb.append(", width=").append(width);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}