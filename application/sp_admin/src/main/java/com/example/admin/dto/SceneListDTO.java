package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列表筛选条件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SceneListDTO {
    @ApiModelProperty(value = "空间id", example = "1")
    private Integer spaceId;

    @ApiModelProperty(value = "场景id", example = "1")
    private Integer sceneId;

    @ApiModelProperty(value = "场景名称", example = "故宫")
    private String sceneName;

    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;
}
