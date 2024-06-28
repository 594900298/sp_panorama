package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SceneAddDTO {
    @ApiModelProperty(value = "空间id", required = true, example = "1")
    @NotNull(message = "请输入空间id")
    private Integer spaceId;

    @ApiModelProperty(value = "场景名称", required = true, example = "故宫")
    @NotNull(message = "请输入场景名称")
    private String sceneName;

    @ApiModelProperty(value = "全景图片链接", required = true, example = "url")
    @NotNull(message = "请先上传全景图片")
    private String panoramicImage;
}
