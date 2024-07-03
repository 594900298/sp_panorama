package com.example.admin.dto;

import com.example.common.enums.HotspotStyle;
import com.example.common.enums.HotspotType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotspotAddDTO {
    @ApiModelProperty(value = "场景id", required = true, example = "1")
    @NotNull(message = "场景id不能为空")
    private Integer sceneId;

    @ApiModelProperty(value = "热点名称", required = true, example = "故宫")
    @NotNull(message = "热点名称不能为空")
    private String hotspotName;

    @ApiModelProperty(value = "热点样式", required = true, example = "skin_hotspotstyle_hotspot")
    @NotNull(message = "热点样式不能为空")
    private HotspotStyle hotspotStyle;

    @ApiModelProperty(value = "热点类型", example = "CHANGE_SCENE切换场景热点 PRODUCT_HOTSPOT货架热点 VIDEO_HOTSPOT视频热点")
    @NotNull(message = "热点类型不能为空")
    private HotspotType hotspotType;

    @ApiModelProperty(value = "关联值", required = true, example = "id|url")
    @NotNull(message = "热点关联值不能为空")
    private String hotspotValue;

    @ApiModelProperty(value = "全景中球体坐标的水平位置", required = true, example = "1")
    @NotNull(message = "全景中球体坐标的水平位置不能为空")
    private String ath;

    @ApiModelProperty(value = "全景中球体坐标的垂直位置", required = true, example = "1")
    @NotNull(message = "全景中球体坐标的垂直位置不能为空")
    private String atv;

    @ApiModelProperty(value = "高度", example = "1")
    private String height;

    @ApiModelProperty(value = "宽度", example = "1")
    private String width;
}
