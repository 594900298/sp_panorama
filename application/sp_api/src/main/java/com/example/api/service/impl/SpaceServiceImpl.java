package com.example.api.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.mapper.SpaceMapper;
import com.example.api.po.SpaceDetailPO;
import com.example.api.service.SpaceService;
import com.example.api.vo.HotspotListVO;
import com.example.api.vo.SceneListVO;
import com.example.api.vo.SpaceDetailVO;
import com.example.common.exception.ServiceException;
import com.example.common.po.Space;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Service实现
 * @createDate 2024-06-25 11:10:11
 */
@Service
public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, Space> implements SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

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
            // 读取
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File("/static/baseXml/api.xml").getAbsolutePath());
            //获取根节点
            Element rootElement = document.getRootElement();
            Element includeEl = rootElement.element("include");
            includeEl.addAttribute("url", StrUtil.format("/static/scene/{}", includeEl.attributeValue("url")));
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




