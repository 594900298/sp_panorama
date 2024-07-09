package com.example.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelConfigVO {
    @ApiModelProperty(value = "平铺图像宽度", example = "1920")
    private String tiledimagewidth;
    @ApiModelProperty(value = "平铺图像高度", example = "1920")
    private String tiledimageheight;
    @ApiModelProperty(value = "url", example = "")
    private String url;
}
