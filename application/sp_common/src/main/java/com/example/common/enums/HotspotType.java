package com.example.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 热点类型
 */
public enum HotspotType {
    CHANGE_SCENE("CHANGE_SCENE", "CHANGE_SCENE"),
    PRODUCT_HOTSPOT("PRODUCT_HOTSPOT", "PRODUCT_HOTSPOT"),
    VIDEO_HOTSPOT("VIDEO_HOTSPOT", "VIDEO_HOTSPOT"),
    ;

    @EnumValue
    private String key;
    @JsonValue
    private String value;

    HotspotType(String key, String value) {
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
