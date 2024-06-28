package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.Control;
import com.example.common.enums.Limitview;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 场景表
 *
 * @TableName sp_scene
 */
@TableName(value = "sp_scene")
@Data
public class Scene implements Serializable {
    @ApiModelProperty(value = "场景id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer sceneId;

    @ApiModelProperty(value = "空间id", example = "1")
    private Integer spaceId;

    @ApiModelProperty(value = "场景名称", example = "故宫")
    private String sceneName;

    @ApiModelProperty(value = "起始水平视角", example = "90")
    private String hlookat;

    @ApiModelProperty(value = "起始垂直视角", example = "90")
    private String vlookat;

    @ApiModelProperty(value = "默认视角的缩放", example = "90")
    private String fov;

    @ApiModelProperty(value = "视角类型", example = "90")
    private Limitview limitview;

    @ApiModelProperty(value = "最小水平视角范围", example = "90")
    private String hlookatmin;

    @ApiModelProperty(value = "最大水平视角范围", example = "90")
    private String hlookatmax;

    @ApiModelProperty(value = "最小垂直视角范围", example = "90")
    private String vlookatmin;

    @ApiModelProperty(value = "最大垂直视角范围", example = "90")
    private String vlookatmax;

    @ApiModelProperty(value = "控制方式 drag[默认]、moveto", example = "DRAG/MOVETO")
    private Control control;

    @ApiModelProperty(value = "随机生成的字符串", example = "scene_8fbe40cdfd2fc681c7d9c9f0832ae52e")
    private String randomString;

    @ApiModelProperty(value = "素材路径", example = "/scene/material/scene_8fbe40cdfd2fc681c7d9c9f0832ae52e/")
    private String panosPath;

    @ApiModelProperty(value = "xml路径", example = "/scene/scene_8fbe40cdfd2fc681c7d9c9f0832ae52e.xml")
    private String xmlPath;

    @ApiModelProperty(value = "素材文件名", example = "34.tiles")
    private String materialFileName;

    @ApiModelProperty(value = "xml level配置", example = "[{\"tiledimagewidth\":\"4736\",\"tiledimageheight\":\"4736\",\"url\":\"/%s/l4/%0v/l4_%s_%0v_%0h.jpg\"}...]")
    private String levelConfig;

    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "50")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", example = "1647412311")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    private Integer deleted;

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
        Scene other = (Scene) that;
        return (this.getSceneId() == null ? other.getSceneId() == null : this.getSceneId().equals(other.getSceneId()))
                && (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
                && (this.getSceneName() == null ? other.getSceneName() == null : this.getSceneName().equals(other.getSceneName()))
                && (this.getHlookat() == null ? other.getHlookat() == null : this.getHlookat().equals(other.getHlookat()))
                && (this.getVlookat() == null ? other.getVlookat() == null : this.getVlookat().equals(other.getVlookat()))
                && (this.getFov() == null ? other.getFov() == null : this.getFov().equals(other.getFov()))
                && (this.getLimitview() == null ? other.getLimitview() == null : this.getLimitview().equals(other.getLimitview()))
                && (this.getHlookatmin() == null ? other.getHlookatmin() == null : this.getHlookatmin().equals(other.getHlookatmin()))
                && (this.getHlookatmax() == null ? other.getHlookatmax() == null : this.getHlookatmax().equals(other.getHlookatmax()))
                && (this.getVlookatmin() == null ? other.getVlookatmin() == null : this.getVlookatmin().equals(other.getVlookatmin()))
                && (this.getVlookatmax() == null ? other.getVlookatmax() == null : this.getVlookatmax().equals(other.getVlookatmax()))
                && (this.getControl() == null ? other.getControl() == null : this.getControl().equals(other.getControl()))
                && (this.getRandomString() == null ? other.getRandomString() == null : this.getRandomString().equals(other.getRandomString()))
                && (this.getPanosPath() == null ? other.getPanosPath() == null : this.getPanosPath().equals(other.getPanosPath()))
                && (this.getXmlPath() == null ? other.getXmlPath() == null : this.getXmlPath().equals(other.getXmlPath()))
                && (this.getMaterialFileName() == null ? other.getMaterialFileName() == null : this.getMaterialFileName().equals(other.getMaterialFileName()))
                && (this.getLevelConfig() == null ? other.getLevelConfig() == null : this.getLevelConfig().equals(other.getLevelConfig()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSceneId() == null) ? 0 : getSceneId().hashCode());
        result = prime * result + ((getSpaceId() == null) ? 0 : getSpaceId().hashCode());
        result = prime * result + ((getSceneName() == null) ? 0 : getSceneName().hashCode());
        result = prime * result + ((getHlookat() == null) ? 0 : getHlookat().hashCode());
        result = prime * result + ((getVlookat() == null) ? 0 : getVlookat().hashCode());
        result = prime * result + ((getFov() == null) ? 0 : getFov().hashCode());
        result = prime * result + ((getLimitview() == null) ? 0 : getLimitview().hashCode());
        result = prime * result + ((getHlookatmin() == null) ? 0 : getHlookatmin().hashCode());
        result = prime * result + ((getHlookatmax() == null) ? 0 : getHlookatmax().hashCode());
        result = prime * result + ((getVlookatmin() == null) ? 0 : getVlookatmin().hashCode());
        result = prime * result + ((getVlookatmax() == null) ? 0 : getVlookatmax().hashCode());
        result = prime * result + ((getControl() == null) ? 0 : getControl().hashCode());
        result = prime * result + ((getRandomString() == null) ? 0 : getRandomString().hashCode());
        result = prime * result + ((getPanosPath() == null) ? 0 : getPanosPath().hashCode());
        result = prime * result + ((getXmlPath() == null) ? 0 : getXmlPath().hashCode());
        result = prime * result + ((getMaterialFileName() == null) ? 0 : getMaterialFileName().hashCode());
        result = prime * result + ((getLevelConfig() == null) ? 0 : getLevelConfig().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sceneId=").append(sceneId);
        sb.append(", spaceId=").append(spaceId);
        sb.append(", sceneName=").append(sceneName);
        sb.append(", hlookat=").append(hlookat);
        sb.append(", vlookat=").append(vlookat);
        sb.append(", fov=").append(fov);
        sb.append(", limitview=").append(limitview);
        sb.append(", hlookatmin=").append(hlookatmin);
        sb.append(", hlookatmax=").append(hlookatmax);
        sb.append(", vlookatmin=").append(vlookatmin);
        sb.append(", vlookatmax=").append(vlookatmax);
        sb.append(", control=").append(control);
        sb.append(", randomString=").append(randomString);
        sb.append(", panosPath=").append(panosPath);
        sb.append(", xmlPath=").append(xmlPath);
        sb.append(", materialFileName=").append(materialFileName);
        sb.append(", levelConfig=").append(levelConfig);
        sb.append(", isShow=").append(isShow);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", Deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}