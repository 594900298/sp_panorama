package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单表
 *
 * @TableName sp_order
 */
@TableName(value = "sp_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPO implements Serializable {
    @ApiModelProperty(value = "订单id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty(value = "订单关联用户id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "订单号", example = "SN17119546173992864")
    private String orderCode;

    @ApiModelProperty(value = "订单金额", example = "10.00")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "支付金额", example = "10.00")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "省id", example = "1")
    private Integer provinceId;

    @ApiModelProperty(value = "市id", example = "1")
    private Integer cityId;

    @ApiModelProperty(value = "区id", example = "1")
    private Integer districtId;

    @ApiModelProperty(value = "收货人名称", example = "半夏")
    private String addressName;

    @ApiModelProperty(value = "收货人手机号码", example = "13800000001")
    private String addressMobile;

    @ApiModelProperty(value = "收货地址", example = "上海浦东新区")
    private String orderAddress;

    @ApiModelProperty(value = "客户备注", example = "半夏很棒😊")
    private String clientRemark;

    @ApiModelProperty(value = "系统备注", example = "半夏很棒😊")
    private String systemRemark;

    @ApiModelProperty(value = "订单状态", example = "")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "收货时间", example = "")
    private Integer deliveryTime;

    @ApiModelProperty(value = "支付时间", example = "1712044831")
    private Integer payTime;

    @ApiModelProperty(value = "更新时间", example = "1712044831")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

    @ApiModelProperty(value = "创建时间", example = "1712044831")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderPO other = (OrderPO) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOrderCode() == null ? other.getOrderCode() == null : this.getOrderCode().equals(other.getOrderCode()))
                && (this.getOrderPrice() == null ? other.getOrderPrice() == null : this.getOrderPrice().equals(other.getOrderPrice()))
                && (this.getPaymentAmount() == null ? other.getPaymentAmount() == null : this.getPaymentAmount().equals(other.getPaymentAmount()))
                && (this.getProvinceId() == null ? other.getProvinceId() == null : this.getProvinceId().equals(other.getProvinceId()))
                && (this.getCityId() == null ? other.getCityId() == null : this.getCityId().equals(other.getCityId()))
                && (this.getDistrictId() == null ? other.getDistrictId() == null : this.getDistrictId().equals(other.getDistrictId()))
                && (this.getAddressName() == null ? other.getAddressName() == null : this.getAddressName().equals(other.getAddressName()))
                && (this.getAddressMobile() == null ? other.getAddressMobile() == null : this.getAddressMobile().equals(other.getAddressMobile()))
                && (this.getOrderAddress() == null ? other.getOrderAddress() == null : this.getOrderAddress().equals(other.getOrderAddress()))
                && (this.getClientRemark() == null ? other.getClientRemark() == null : this.getClientRemark().equals(other.getClientRemark()))
                && (this.getSystemRemark() == null ? other.getSystemRemark() == null : this.getSystemRemark().equals(other.getSystemRemark()))
                && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
                && (this.getDeliveryTime() == null ? other.getDeliveryTime() == null : this.getDeliveryTime().equals(other.getDeliveryTime()))
                && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderCode() == null) ? 0 : getOrderCode().hashCode());
        result = prime * result + ((getOrderPrice() == null) ? 0 : getOrderPrice().hashCode());
        result = prime * result + ((getPaymentAmount() == null) ? 0 : getPaymentAmount().hashCode());
        result = prime * result + ((getProvinceId() == null) ? 0 : getProvinceId().hashCode());
        result = prime * result + ((getCityId() == null) ? 0 : getCityId().hashCode());
        result = prime * result + ((getDistrictId() == null) ? 0 : getDistrictId().hashCode());
        result = prime * result + ((getAddressName() == null) ? 0 : getAddressName().hashCode());
        result = prime * result + ((getAddressMobile() == null) ? 0 : getAddressMobile().hashCode());
        result = prime * result + ((getOrderAddress() == null) ? 0 : getOrderAddress().hashCode());
        result = prime * result + ((getClientRemark() == null) ? 0 : getClientRemark().hashCode());
        result = prime * result + ((getSystemRemark() == null) ? 0 : getSystemRemark().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getDeliveryTime() == null) ? 0 : getDeliveryTime().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", orderPrice=").append(orderPrice);
        sb.append(", paymentAmount=").append(paymentAmount);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityId=").append(cityId);
        sb.append(", districtId=").append(districtId);
        sb.append(", addressName=").append(addressName);
        sb.append(", addressMobile=").append(addressMobile);
        sb.append(", orderAddress=").append(orderAddress);
        sb.append(", clientRemark=").append(clientRemark);
        sb.append(", systemRemark=").append(systemRemark);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", deliveryTime=").append(deliveryTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}