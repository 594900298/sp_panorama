package com.example.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class SpaceListVO implements Serializable {
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
     * 封面
     */
    @ApiModelProperty(value = "封面", example = "url")
    private String spaceThumb;
}