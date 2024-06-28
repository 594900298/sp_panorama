package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerPaginateVO {
    @ApiModelProperty(value = "轮播id", example = "1")
    private Integer bannerId;

    @ApiModelProperty(value = "轮播名称", example = "1")
    private String bannerName;

    @ApiModelProperty(value = "轮播图", example = "url")
    private String bannerImage;

    @ApiModelProperty(value = "跳转连接", example = "url")
    private String bannerLink;

    @ApiModelProperty(value = "展示状态", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;
}
