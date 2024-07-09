package com.example.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 空间表
 *
 * @TableName sp_space
 */
@TableName(value = "sp_space")
@Data
public class SpaceDetailVO implements Serializable {
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

    @ApiModelProperty(value = "场景列表", example = "List<SceneDetailVo>")
    private List<SceneListVO> sceneListVO;
}