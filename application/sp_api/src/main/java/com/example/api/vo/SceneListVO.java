package com.example.api.vo;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.common.enums.Control;
import com.example.common.enums.Limitview;
import com.example.common.utils.DomainUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Data
public class SceneListVO implements Serializable {
    @ApiModelProperty(value = "场景id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer sceneId;

    @ApiModelProperty(value = "空间id", example = "1")
    private Integer spaceId;

    @ApiModelProperty(value = "场景名称", example = "故宫")
    private String sceneName;

    @ApiModelProperty(value = "视角类型", example = "AUTO/LOOKAT/RANGE/FULLRAGE/OFFRANGE")
    private Limitview limitview;

    @ApiModelProperty(value = "起始水平视角", example = "90")
    private String hlookat;

    @ApiModelProperty(value = "起始垂直视角", example = "90")
    private String vlookat;

    @ApiModelProperty(value = "默认视角的缩放", example = "113.66")
    private String fov;

    @ApiModelProperty(value = "最小水平视角范围", example = "90")
    private String hlookatmin;

    @ApiModelProperty(value = "最大水平视角范围", example = "90")
    private String hlookatmax;

    @ApiModelProperty(value = "最小垂直视角范围", example = "90")
    private String vlookatmin;

    @ApiModelProperty(value = "最大垂直视角范围", example = "90")
    private String vlookatmax;

    @ApiModelProperty(value = "控制方式 drag[默认]、moveto", example = "DRAG/MOVETO")
    private Control control;

    @ApiModelProperty(value = "随机生成的字符串", example = "scene_8fbe40cdfd2fc681c7d9c9f0832ae52e")
    private String randomString;

    @ApiModelProperty(value = "素材路径", example = "/scene/material/scene_8fbe40cdfd2fc681c7d9c9f0832ae52e/")
    private String panosPath;

    @ApiModelProperty(value = "xml路径", example = "/scene/scene_8fbe40cdfd2fc681c7d9c9f0832ae52e.xml")
    private String xmlPath;

    @ApiModelProperty(value = "素材文件名", example = "34.tiles")
    private String materialFileName;

    @ApiModelProperty(value = "xml level配置", example = "[{\"tiledimagewidth\":\"4736\",\"tiledimageheight\":\"4736\",\"url\":\"/%s/l4/%0v/l4_%s_%0v_%0h.jpg\"}...]")
    private JSONArray levelConfig;

    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "50")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", example = "1647412311")
    private Integer createTime;

    @ApiModelProperty(value = "封面", example = "[path]/thumb.jpg")
    private String thumb;

    @ApiModelProperty(value = "热点列表", example = "List<HotspotListVO>")
    private List<HotspotListVO> hotspotListVO;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getThumb() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return StrUtil.format("{}/static/scene/material/{}/panos/{}/thumb.jpg", DomainUtil.getCurrentDomain(request), randomString, materialFileName);
    }
}