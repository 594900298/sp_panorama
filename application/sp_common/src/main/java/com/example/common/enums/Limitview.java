package com.example.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 视角类型
 */
public enum Limitview {
    AUTO("auto", "自动限制"),
    LOOKAT("lookat", "限制查看"),
    RANGE("range", "只有在这个范围内,允许观察"),
    FULLRAGE("fullrage", "但允许放大看到整个图像"),
    OFFRANGE("offrange", "不以任何方式限制缩放"),
    ;
    @EnumValue
    private String key;
    @JsonValue
    private String value;

    Limitview(String key, String value) {
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
