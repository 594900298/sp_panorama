package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerAddDTO {
    @ApiModelProperty(value = "名称", required = true, example = "banner")
    @NotNull(message = "名称不能为空")
    private String bannerName;

    @ApiModelProperty(value = "轮播图", required = true, example = "url")
    @NotNull(message = "请先上传轮播图")
    private String bannerImage;

    @ApiModelProperty(value = "跳转连接", example = "url")
    private String bannerLink;

    @ApiModelProperty(value = "显示状态", required = true, example = "true")
    @NotNull(message = "缺少显示状态")
    private Boolean isShow;
}
