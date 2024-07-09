package com.example.admin.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.HotspotStyle;
import com.example.common.enums.HotspotType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "sp_space")
@Data
public class HotspotListVO implements Serializable {
    @ApiModelProperty(value = "热点id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer hotspotId;

    @ApiModelProperty(value = "场景id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer sceneId;

    @ApiModelProperty(value = "热点名称", example = "故宫")
    private String hotspotName;

    @ApiModelProperty(value = "随机生成的字符串", example = "hotspot_8697e571d2957c4f59f34feb68109808")
    private String randomString;

    @ApiModelProperty(value = "热点样式", example = "skin_hotspotstyle_hotspot")
    private HotspotStyle hotspotStyle;

    @ApiModelProperty(value = "热点类型", example = "CHANGE_SCENE切换场景热点 PRODUCT_HOTSPOT货架热点 VIDEO_HOTSPOT视频热点")
    private HotspotType hotspotType;

    @ApiModelProperty(value = "关联值", required = true, example = "id|url")
    private String hotspotValue;

    @ApiModelProperty(value = "全景中球体坐标的水平位置", example = "1")
    private String ath;

    @ApiModelProperty(value = "全景中球体坐标的垂直位置", example = "1")
    private String atv;

    @ApiModelProperty(value = "高度", example = "1")
    private String height;

    @ApiModelProperty(value = "宽度", example = "1")
    private String width;

    @ApiModelProperty(value = "创建时间", example = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}