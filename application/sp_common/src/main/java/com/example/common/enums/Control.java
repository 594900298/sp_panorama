package com.example.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 控制方式
 */
public enum Control {
    DRAG("drag", "拖动全景图像"),
    MOVETO("moveto", "移动全景图像"),
    ;
    @EnumValue
    private String key;
    @JsonValue
    private String value;

    Control(String key, String value) {
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
