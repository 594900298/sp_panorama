package com.example.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.common.enums.Limitview;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ScenePaginateVo implements Serializable {
    @ApiModelProperty(value = "场景id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer sceneId;

    @ApiModelProperty(value = "场景名称", example = "故宫")
    private String sceneName;

    @ApiModelProperty(value = "视角类型", example = "AUTO/LOOKAT/RANGE/FULLRAGE/OFFRANGE")
    private Limitview limitview;

    @ApiModelProperty(value = "最小水平视角范围", example = "90")
    private String hlookatmin;

    @ApiModelProperty(value = "最大水平视角范围", example = "90")
    private String hlookatmax;

    @ApiModelProperty(value = "最小垂直视角范围", example = "90")
    private String vlookatmin;

    @ApiModelProperty(value = "最大垂直视角范围", example = "90")
    private String vlookatmax;


    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "50")
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}