package com.example.admin.po;

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
public class OrderDetailPO {
    @ApiModelProperty(value = "订单id", example = "1")
    private Integer orderId;

    @ApiModelProperty(value = "订单号", example = "admin")
    private String orderCode;

    @ApiModelProperty(value = "订单金额", example = "10")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "支付金额", example = "10")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "收货人名称", example = "半夏")
    private String addressName;

    @ApiModelProperty(value = "收货人手机号", example = "13800000001")
    private String addressMobile;

    @ApiModelProperty(value = "收货地址", example = "上海市浦东新区")
    private String orderAddress;

    @ApiModelProperty(value = "客户备注", example = "")
    private String clientRemark;

    @ApiModelProperty(value = "系统备注", example = "")
    private String systemRemark;

    @ApiModelProperty(value = "订单状态", example = "WAITPAY")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "创建时间", example = "1712053702")
    private Integer createTime;

    @ApiModelProperty(value = "购买快照信息", example = "")
    private List<OrderDetailGoodsSnapshotPO> orderGoodsSnapshot;
}
