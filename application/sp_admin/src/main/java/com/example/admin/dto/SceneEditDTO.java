package com.example.admin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.common.enums.Control;
import com.example.common.enums.Limitview;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SceneEditDTO {
    @ApiModelProperty(value = "场景id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer sceneId;

    @ApiModelProperty(value = "空间id", example = "1")
    private Integer spaceId;

    @ApiModelProperty(value = "场景名称", example = "故宫")
    private String sceneName;

    @ApiModelProperty(value = "视角类型", example = "AUTO/LOOKAT/RANGE/FULLRAGE/OFFRANGE")
    private Limitview limitview;

    @ApiModelProperty(value = "起始水平视角", example = "90")
    private String hlookat;

    @ApiModelProperty(value = "起始垂直视角", example = "90")
    private String vlookat;

    @ApiModelProperty(value = "默认视角的缩放", example = "113.66")
    private String fov;

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
}
