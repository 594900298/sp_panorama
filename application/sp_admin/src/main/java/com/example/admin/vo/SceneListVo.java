package com.example.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SceneListVo implements Serializable {
    @ApiModelProperty(value = "场景id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer sceneId;

    @ApiModelProperty(value = "场景名称", example = "故宫")
    private String sceneName;

    @ApiModelProperty(value = "随机生成的字符串", example = "scene_8fbe40cdfd2fc681c7d9c9f0832ae52e")
    private String randomString;
    @ApiModelProperty(value = "素材文件名", example = "34.tiles")
    private String materialFileName;

    @ApiModelProperty(value = "封面", example = "[path]/thumb.jpg")
    private String thumb;

    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "50")
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getMaterialFileName() {
        return "/scene/material/" + randomString + "/panos/" + materialFileName + "/thumb.jpg";
    }
}