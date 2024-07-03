package com.example.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 热点样式
 */
public enum HotspotStyle {
    SKIN_PROD_LIST("skin_prod_list", "skin_prod_list"),
    SKIN_HOTSPOTSTYLE_TURN_LEFT("skin_hotspotstyle_turn_left", "skin_hotspotstyle_turn_left"),
    SKIN_HOTSPOTSTYLE_TURN_RIGHT("skin_hotspotstyle_turn_right", "skin_hotspotstyle_turn_right"),
    SKIN_HOTSPOTSTYLE_TURN_LEFT_BACK("skin_hotspotstyle_turn_left_back", "skin_hotspotstyle_turn_left_back"),
    SKIN_HOTSPOTSTYLE_TURN_RIGHT_BACK("skin_hotspotstyle_turn_right_back", "skin_hotspotstyle_turn_right_back"),
    SKIN_HOTSPOTSTYLE_ARROW("skin_hotspotstyle_arrow", "skin_hotspotstyle_arrow"),
    SKIN_LAYERSTYLE_TIP("skin_layerstyle_tip", "skin_layerstyle_tip"),
    SKIN_HOTSPOTSTYLE("skin_hotspotstyle", "skin_hotspotstyle"),
    SKIN_HOTSPOTSTYLE_HOTSPOT("skin_hotspotstyle_hotspot", "skin_hotspotstyle_hotspot"),
    SKIN_HOTSPOTSTYLE_INSPIRATION("skin_hotspotstyle_inspiration", "skin_hotspotstyle_inspiration"),
    SKIN_HOTSPOTSTYLE_VIDEO("skin_hotspotstyle_video", "skin_hotspotstyle_video"),
    SKIN_HOTSPOTSTYLE_POINT("skin_hotspotstyle_point", "skin_hotspotstyle_point"),
    SKIN_HOTSPOTSTYLE_DOOR("skin_hotspotstyle_door", "skin_hotspotstyle_door"),
    ;
    @EnumValue
    private String key;
    @JsonValue
    private String value;

    HotspotStyle(String key, String value) {
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
