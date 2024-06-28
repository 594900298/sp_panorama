package com.example.api.dto;

import com.example.common.enums.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaginateDTO {
    @ApiModelProperty(value = "支付状态", example = "未支付/待发货/待收货/已完成/取消")
    private OrderStatus orderStatus;
}
