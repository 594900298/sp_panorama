package com.example.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 修改排序DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEditSortDTO {
    @ApiModelProperty(value = "规则id", required = true, example = "1")
    @NotNull(message = "主键不能为空")
    private Integer spaceId;

    @ApiModelProperty(value = "排序", required = true, example = "1")
    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 999, message = "排序只能填写0-999")
    private Integer sort;
}
