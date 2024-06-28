package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCollectCancelDTO {
    @ApiModelProperty(value = "商品id", example = "1")
    @NotNull(message = "缺少商品id")
    private Integer goodsId;
}
