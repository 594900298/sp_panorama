package com.example.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单状态
 */
public enum OrderStatus {
    WAIT_PAY("WAIT_PAY", "未支付"),
    WAIT_SEND("WAIT_SEND", "待发货"),
    WAIT_TAKE("WAIT_TAKE", "待收货"),
    COMPLETED("COMPLETED", "已完成"),
    CANCEL("CANCEL", "取消"),
    AFTER_SALES("AFTER_SALES", "退款/售后"),
    ;
    @EnumValue
    private String key;
    @JsonValue
    private String value;

    OrderStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
