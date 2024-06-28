package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConfirmInfoDTO {
    @ApiModelProperty(value = "缺少商品id", example = "1")
    @NotNull(message = "缺少商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "对应sku码", example = "f4a0a56a-9aac-4a9c-8fc3-bdce5a288cfb")
    @NotNull(message = "缺少sku码")
    private String skuCode;

    @ApiModelProperty(value = "数量", example = "1")
    @NotNull(message = "缺少数量")
    private Short quantity;
}
