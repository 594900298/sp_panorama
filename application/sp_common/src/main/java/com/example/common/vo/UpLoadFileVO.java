package com.example.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpLoadFileVO {
    @ApiModelProperty(value = "文件名", example = "半夏商城.jar")
    private String fileName;

    @ApiModelProperty(value = "文件大小字节", example = "188888")
    private Long fileSize;

    @ApiModelProperty(value = "文件保存路径", example = "")
    private String fullPath;

    @ApiModelProperty(value = "文件网络路径", example = "")
    private String fileUrl;
}
