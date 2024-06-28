package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceAddDTO {
    @ApiModelProperty(value = "名称", required = true, example = "故宫")
    @NotNull(message = "名称不能为空")
    private String spaceName;

    @ApiModelProperty(value = "封面", example = "url")
    private String spaceThumb;

    @ApiModelProperty(value = "显示状态", required = true, example = "url")
    @NotNull(message = "显示状态不能为空")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 999, message = "排序只能填写0-999")
    private Integer sort;
}
