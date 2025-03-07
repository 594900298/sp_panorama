package com.example.api.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.mapper.SpaceMapper;
import com.example.api.po.SpaceDetailPO;
import com.example.api.service.SpaceService;
import com.example.api.vo.*;
import com.example.common.bo.PageParamBO;
import com.example.common.exception.ServiceException;
import com.example.common.po.SpacePO;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Service实现
 * @createDate 2024-06-25 11:10:11
 */
@Service
public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, SpacePO> implements SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    /**
     * 列表数据
     * @return
     */
    @Override
    public List<SpaceListVO> getList() {
        return (List<SpaceListVO>) spaceMapper.selectList(
                new QueryWrapper<SpacePO>()
                        .select("space_id","space_name","space_thumb")
                        .lambda()
                        .eq(SpacePO::getIsShow, true)
                        .orderByAsc(SpacePO::getSort)
        ).stream().map(po -> {
            SpaceListVO vo = new SpaceListVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 分页数据
     * @param pageParamBO
     * @return
     */
    @Override
    public IPage getPaginate(PageParamBO pageParamBO) {
        // 查询数据
        return spaceMapper.selectPage(
                new Page<>(pageParamBO.getPageIndex(), pageParamBO.getPageSize()),
                new QueryWrapper<SpacePO>()
                        .select("space_id","space_name","space_thumb")
                        .lambda()
                        .eq(SpacePO::getIsShow, true)
                        .orderByAsc(SpacePO::getSort)
        ).convert(po -> {
            SpacePaginateVO v = new SpacePaginateVO();
            BeanUtils.copyProperties(po, v);
            return v;
        });
    }

    /**
     * 详情
     *
     * @param spaceId
     * @return
     */
    @Override
    public SpaceDetailVO detail(Integer spaceId) {
        SpaceDetailPO po = spaceMapper.selectJoinById(spaceId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        SpaceDetailVO vo = new SpaceDetailVO();
        BeanUtils.copyProperties(po, vo);
        vo.setSceneListVO(po.getSceneListPO().stream().map(p -> {
            SceneListVO v = new SceneListVO();
            BeanUtils.copyProperties(p, v);
            v.setLevelConfig(JSON.parseArray(p.getLevelConfig()));
            v.setHotspotListVO(p.getHotspotListPO().stream().map(pp -> {
                HotspotListVO vv = new HotspotListVO();
                BeanUtils.copyProperties(pp, vv);
                return vv;
            }).collect(Collectors.toList()));
            return v;
        }).collect(Collectors.toList()));
        return vo;
    }

    @Override
    public String getXml(Integer spaceId) {
        // 获取出数据
        SpaceDetailVO vo = detail(spaceId);
        // 将场景List转换成Map
        Map<String, SceneListVO> sceneMap = new HashMap<>();
        for (int i = 0; i < vo.getSceneListVO().size(); i++) {
            SceneListVO item = vo.getSceneListVO().get(i);
            sceneMap.put(item.getSceneId().toString(), item);
        }
        // 加载xml
        try {
            Document document = DocumentHelper.createDocument();
            // 写入根节点
            Element rootElement = document.addElement("krpano").addAttribute("version", "1.19").addAttribute("title", "Virtual Tour");
            // 写入include
            rootElement.addElement("include").addAttribute("url", "/static/scene/skin/vtourskin.xml");
            // 写入skin_settings
            rootElement.addElement("skin_settings")
                    .addAttribute("maps", "false")
                    .addAttribute("maps_type", "google")
                    .addAttribute("maps_bing_api_key", "")
                    .addAttribute("maps_google_api_key", "")
                    .addAttribute("maps_zoombuttons", "false")
                    .addAttribute("gyro", "true")
                    .addAttribute("webvr", "false")
                    .addAttribute("webvr_gyro_keeplookingdirection", "false")
                    .addAttribute("webvr_prev_next_hotspots", "true")
                    .addAttribute("littleplanetintro", "false")
                    .addAttribute("title", "false")
                    .addAttribute("thumbs", "false")
                    .addAttribute("thumbs_width", "120")
                    .addAttribute("thumbs_height", "80")
                    .addAttribute("thumbs_padding", "10")
                    .addAttribute("thumbs_crop", "0|40|240|160")
                    .addAttribute("thumbs_opened", "false")
                    .addAttribute("thumbs_text", "false")
                    .addAttribute("thumbs_dragging", "true")
                    .addAttribute("thumbs_onhoverscrolling", "false")
                    .addAttribute("thumbs_scrollbuttons", "false")
                    .addAttribute("thumbs_scrollindicator", "false")
                    .addAttribute("thumbs_loop", "false")
                    .addAttribute("tooltips_buttons", "false")
                    .addAttribute("tooltips_thumbs", "false")
                    .addAttribute("tooltips_hotspots", "false")
                    .addAttribute("tooltips_mapspots", "false")
                    .addAttribute("deeplinking", "false")
                    .addAttribute("loadscene_flags", "MERGE")
                    .addAttribute("loadscene_blend", "OPENBLEND(0.5, 0.0, 0.75, 0.05, linear)")
                    .addAttribute("loadscene_blend_prev", "SLIDEBLEND(0.5, 180, 0.75, linear)")
                    .addAttribute("loadscene_blend_next", "SLIDEBLEND(0.5,   0, 0.75, linear)")
                    .addAttribute("loadingtext", "loading...")
                    .addAttribute("layout_width", "100%")
                    .addAttribute("layout_maxwidth", "814")
                    .addAttribute("controlbar_width", "-24")
                    .addAttribute("controlbar_height", "40")
                    .addAttribute("controlbar_offset", "20")
                    .addAttribute("controlbar_offset_closed", "-40")
                    .addAttribute("controlbar_overlap.no-fractionalscaling", "10")
                    .addAttribute("controlbar_overlap.fractionalscaling", "0")
                    .addAttribute("design_skin_images", "vtourskin.png")
                    .addAttribute("design_bgcolor", "0x2D3E50")
                    .addAttribute("design_bgalpha", "0.8")
                    .addAttribute("design_bgborder", "0")
                    .addAttribute("design_bgroundedge", "1")
                    .addAttribute("design_bgshadow", "0 4 10 0x000000 0.3")
                    .addAttribute("design_thumbborder_bgborder", "3 0xFFFFFF 1.0")
                    .addAttribute("design_thumbborder_padding", "2")
                    .addAttribute("design_thumbborder_bgroundedge", "0")
                    .addAttribute("design_text_css", "color:#FFFFFF; font-family:Arial;")
                    .addAttribute("design_text_shadow", "1");
            // 写入配合的方法
            rootElement.addElement("action").addAttribute("name", "startup").addAttribute("autorun", "onstart").setText("if(startscene === null OR !scene[get(startscene)], copy(startscene,scene[0].name); );\n" +
                    "\t\tloadscene(get(startscene), null, MERGE);\n" +
                    "\t\tif(startactions !== null, startactions() );");
            rootElement.addElement("action").addAttribute("name", "draghotspot").setText("spheretoscreen(ath, atv, hotspotcenterx, hotspotcentery,\n" +
                    "\t\tcalc(mouse.stagex LT stagewidth/2 ? 'l' : 'r')); sub(drag_adjustx, mouse.stagex,\n" +
                    "\t\thotspotcenterx); sub(drag_adjusty, mouse.stagey, hotspotcentery); asyncloop(pressed, sub(dx,\n" +
                    "\t\tmouse.stagex, drag_adjustx); sub(dy, mouse.stagey, drag_adjusty); screentosphere(dx, dy,\n" +
                    "\t\tath, atv); js(listenerDragHotSpot(get('xml.scene'),get('name'),get(ath),get(atv))); );");
            rootElement.addElement("action").addAttribute("name", "show_tooltip").setText("txtadd(tooltipname, 'tooltip_', get(name));\n" +
                    "\t\taddplugin(get(tooltipname)); txtadd(plugin[get(tooltipname)].parent, 'hotspot[', get(name),\n" +
                    "\t\t']'); set(plugin[get(tooltipname)].url,'%SWFPATH%/plugins/textfield.swf');\n" +
                    "\t\tset(plugin[get(tooltipname)].alpha,1); set(plugin[get(tooltipname)].align,top);\n" +
                    "\t\tset(plugin[get(tooltipname)].edge,bottom); set(plugin[get(tooltipname)].x,0);\n" +
                    "\t\tset(plugin[get(tooltipname)].y,-20); set(plugin[get(tooltipname)].autowidth,true);\n" +
                    "\t\tset(plugin[get(tooltipname)].autoheight,true); set(plugin[get(tooltipname)].vcenter,true);\n" +
                    "\t\tset(plugin[get(tooltipname)].background,true);\n" +
                    "\t\tset(plugin[get(tooltipname)].backgroundcolor,0x000000);\n" +
                    "\t\tset(plugin[get(tooltipname)].roundedge,5);\n" +
                    "\t\tset(plugin[get(tooltipname)].backgroundalpha,0.65);\n" +
                    "\t\tset(plugin[get(tooltipname)].css,'text-align:center; padding: 5px 10px; color:#FFFFFF;\n" +
                    "\t\tfont-family:MicrosoftYahei; font-size:16px;'); copy(plugin[get(tooltipname)].html,tiptext)");
            rootElement.addElement("action").addAttribute("name", "hide_tooltip").setText("txtadd(tooltipname, 'tooltip_', get(name));\n" +
                    "\t\tset(plugin[get(tooltipname)].alpha,0);");
            rootElement.addElement("action").addAttribute("name", "hide_vtourskin").addAttribute("autorun", "onstart").setText("skin_hideskin('instant');\n" +
                    "\t\tset(layer[skin_scroll_window].visible,false); set(layer[skin_control_bar].visible,false);\n" +
                    "\t\tset(layer[skin_btn_prev_fs].visible,false); set(layer[skin_btn_next_fs].visible,false);");
            Iterator<SceneListVO> it = vo.getSceneListVO().iterator();
            while (it.hasNext()) {
                SceneListVO item = it.next();
                String materialPath = StrUtil.format("/static/scene/material/{}/panos/{}", item.getRandomString(), item.getMaterialFileName());
                //scene 标签
                Element sceneEl = rootElement.addElement("scene")
                        .addAttribute("name", item.getRandomString())
                        .addAttribute("title", item.getSceneName())
                        .addAttribute("onstart", "")
                        .addAttribute("havevrimage", "true")
                        .addAttribute("thumburl", StrUtil.format("{}/thumb.jpg", materialPath))
                        .addAttribute("lat", "")
                        .addAttribute("lng", "")
                        .addAttribute("heading", "");
                //control标签
                sceneEl.addElement("control")
                        .addAttribute("mouse", item.getControl().getKey())
                        .addAttribute("touch", item.getControl().getKey());
                //view标签
                Element viewEl = sceneEl.addElement("view")
                        .addAttribute("hlookat", item.getHlookat())
                        .addAttribute("vlookat", item.getVlookat())
                        .addAttribute("fovtype", "MFOV")
                        .addAttribute("fov", item.getFov())
                        .addAttribute("maxpixelzoom", "2.0")
                        .addAttribute("fovmin", "70")
                        .addAttribute("fovmax", "140")
                        .addAttribute("limitview", item.getLimitview().getKey());
                if (item.getLimitview().getKey() != "auto") {
                    viewEl.addAttribute("hlookatmin", item.getHlookatmin())
                            .addAttribute("hlookatmax", item.getHlookatmax())
                            .addAttribute("vlookatmin", item.getVlookatmin())
                            .addAttribute("vlookatmax", item.getVlookatmax());
                }
                //preview标签
                sceneEl.addElement("preview")
                        .addAttribute("url", StrUtil.format("{}/preview.jpg", materialPath));
                //image标签
                Element imageEl = sceneEl.addElement("image")
                        .addAttribute("type", "CUBE")
                        .addAttribute("multires", "true")
                        .addAttribute("tilesize", "512")
                        .addAttribute("if", "!webvr.isenabled");
                // level标签
                for (int i = 0; i < item.getLevelConfig().size(); i++) {
                    JSONObject jsonObject = item.getLevelConfig().getJSONObject(i);
                    imageEl.addElement("level")
                            .addAttribute("tiledimagewidth", jsonObject.getString("tiledimagewidth"))
                            .addAttribute("tiledimageheight", jsonObject.getString("tiledimageheight"))
                            .addElement("cube")
                            .addAttribute("url", materialPath + jsonObject.getString("url"));
                }
                //image标签
                sceneEl.addElement("image")
                        .addAttribute("if", "webvr.isenabled")
                        .addElement("cube")
                        .addAttribute("url", StrUtil.format("/vr/pano_%s.jpg", materialPath));
                //处理热点
                Iterator<HotspotListVO> ii = item.getHotspotListVO().iterator();
                while (ii.hasNext()) {
                    HotspotListVO vv = ii.next();
                    Element hotspotEl = sceneEl.addElement("hotspot")
                            .addAttribute("name", item.getRandomString())
                            .addAttribute("style", vv.getHotspotStyle().getValue())
                            .addAttribute("ath", vv.getAth())
                            .addAttribute("atv", vv.getAtv());
                    if (Objects.isNull(vv.getWidth()) && !"0".equals(vv.getWidth())) {
                        hotspotEl.addAttribute("width", vv.getWidth());
                    }
                    if (Objects.isNull(vv.getHeight()) && !"0".equals(vv.getHeight())) {
                        hotspotEl.addAttribute("height", vv.getHeight());
                    }
                    switch (vv.getHotspotType().getKey()) {
                        case "CHANGE_SCENE":
                            hotspotEl.addAttribute("onclick", StrUtil.format("js(linkedsceneClick(\"{}\"))", sceneMap.get(vv.getHotspotValue()).getRandomString()));
                            break;
                        case "PRODUCT_HOTSPOT":
                            hotspotEl.addAttribute("onclick", StrUtil.format("js(shelfHotspotClick(\"{}\"))", vv.getHotspotValue()));
                            break;
                        case "VIDEO_HOTSPOT":
                            hotspotEl.addAttribute("onclick", StrUtil.format("js(videoHotspotClick(\"{}\"))", vv.getHotspotValue()));
                            break;
                    }
                }
            }
            return document.asXML();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage().toString(), 106);
        }
    }


}




