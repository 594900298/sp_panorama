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
public class HotspotListDTO {
    @ApiModelProperty(value = "场景id", example = "1")
    private Integer sceneId;
}
