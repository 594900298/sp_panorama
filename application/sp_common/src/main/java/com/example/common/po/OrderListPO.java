package com.example.common.po;

import com.example.common.enums.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListPO {
    @ApiModelProperty(value = "商品id", example = "1")
    private Integer orderId;

    @ApiModelProperty(value = "订单号", example = "SN17119521186649194")
    private String orderCode;

    @ApiModelProperty(value = "订单金额", example = "10.00")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "支付金额", example = "10.00")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "订单状态", example = "")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "创建时间", example = "1712046091")
    private Integer createTime;

    @ApiModelProperty(value = "快照", example = "")
    private List<OrderGoodsSnapshotPO> orderGoodsSnapshotPO;
}
