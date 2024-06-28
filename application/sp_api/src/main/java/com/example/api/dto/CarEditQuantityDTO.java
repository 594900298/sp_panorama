package com.example.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarEditQuantityDTO {
    @ApiModelProperty(value = "商品id", example = "1")
    @NotNull(message = "缺少主键id")
    private Integer carId;

    @ApiModelProperty(value = "数量", example = "1")
    @NotNull(message = "缺少数量")
    private Integer quantity;
}
