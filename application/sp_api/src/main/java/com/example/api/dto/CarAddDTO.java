package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarAddDTO {
    @ApiModelProperty(value = "商品id", example = "1")
    @NotNull(message = "缺少商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "缺少sku码", example = "77d99901-0dc0-4ecc-bc08-6090dc551449")
    @NotNull(message = "缺少sku码")
    private String skuCode;

    @ApiModelProperty(value = "数量", example = "1")
    @NotNull(message = "缺少数量")
    private Short quantity;
}
