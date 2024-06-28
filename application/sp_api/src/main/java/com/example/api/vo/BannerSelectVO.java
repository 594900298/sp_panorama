package com.example.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerSelectVO {
    @ApiModelProperty(value = "轮播id", example = "1")
    private Integer bannerId;

    @ApiModelProperty(value = "轮播名称", example = "1")
    private String bannerName;

    @ApiModelProperty(value = "轮播图", example = "url")
    private String bannerImage;

    @ApiModelProperty(value = "跳转连接", example = "url")
    private String bannerLink;

}
