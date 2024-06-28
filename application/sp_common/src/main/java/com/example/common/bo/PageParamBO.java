package com.example.common.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 分页请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParamBO {
    @ApiModelProperty(value = "当前页数",required = true,example = "1")
    @NotNull(message = "当前页数不能为空")
    private Integer pageIndex;


    @ApiModelProperty(value = "每页显示条目个数",required = true,example = "10")
    @NotNull(message = "每页显示条目个数不能为空")
    private Integer pageSize;
}
