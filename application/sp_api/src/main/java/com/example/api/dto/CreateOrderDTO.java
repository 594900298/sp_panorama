package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    @ApiModelProperty(value = "缺少商品id", example = "1")
    @NotNull(message = "缺少商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "缺少sku码", example = "710d14eb-ec38-49cd-98fb-af7b3284fb31")
    @NotNull(message = "缺少sku码")
    private String skuCode;

    @ApiModelProperty(value = "数量", example = "1")
    @NotNull(message = "缺少数量")
    private Integer quantity;

    @ApiModelProperty(value = "备注", example = "")
    private String remark;
}
