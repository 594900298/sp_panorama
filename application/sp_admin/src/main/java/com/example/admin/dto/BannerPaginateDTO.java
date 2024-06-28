package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轮播图分页DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerPaginateDTO {
    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;
}
