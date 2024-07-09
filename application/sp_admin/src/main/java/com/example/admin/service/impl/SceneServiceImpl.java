package com.example.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.bo.LevelConfigBO;
import com.example.admin.dto.*;
import com.example.admin.service.SceneService;
import com.example.admin.vo.SceneDetailVO;
import com.example.admin.vo.SceneListVO;
import com.example.admin.vo.ScenePaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.enums.Control;
import com.example.common.enums.Limitview;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.SceneMapper;
import com.example.common.po.Scene;
import com.example.common.utils.DomainUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_scene(场景表)】的数据库操作Service实现
 * @createDate 2024-06-25 15:20:51
 */
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, Scene> implements SceneService {
    @Autowired
    private SceneMapper sceneMapper;

    @Autowired
    protected HttpServletRequest request;

    /**
     * 分页
     *
     * @param pageParamBO
     * @param scenePaginateDTO
     * @return
     */
    @Override
    public IPage getPaginate(PageParamBO pageParamBO, ScenePaginateDTO scenePaginateDTO) {
        QueryWrapper queryWrapper = new QueryWrapper<Scene>().select("scene_id", "scene_name", "limitview", "hlookatmin", "hlookatmax", "vlookatmin", "vlookatmax", "is_show", "sort").orderByAsc("sort");
        if (!Objects.isNull(scenePaginateDTO.getSpaceId()) && scenePaginateDTO.getSpaceId() > 0) {
            queryWrapper.eq("space_id", scenePaginateDTO.getSpaceId());
        }
        if (!Objects.isNull(scenePaginateDTO.getSceneId()) && scenePaginateDTO.getSceneId() > 0) {
            queryWrapper.ne("scene_id", scenePaginateDTO.getSceneId());
        }
        if (!Objects.isNull(scenePaginateDTO.getSceneName()) && !"".equals(scenePaginateDTO.getSceneName())) {
            queryWrapper.like("scene_name", "%" + scenePaginateDTO.getSceneName() + "%");
        }
        if (!Objects.isNull(scenePaginateDTO.getIsShow())) {
            queryWrapper.eq("is_show", scenePaginateDTO.getIsShow());
        }
        // 查询数据
        return sceneMapper.selectPage(new Page<Scene>(pageParamBO.getPageIndex(), pageParamBO.getPageSize()), queryWrapper).convert(po -> {
            ScenePaginateVO vo = new ScenePaginateVO();
            BeanUtils.copyProperties(po, vo);
            vo.setLimitview(((Scene) po).getLimitview().getKey());
            return vo;
        });
    }

    /**
     * 列表
     *
     * @param sceneListDTO
     * @return
     */
    @Override
    public List<SceneListVO> getList(SceneListDTO sceneListDTO) {
        QueryWrapper queryWrapper = new QueryWrapper<Scene>().select("scene_id", "scene_name", "random_string", "material_file_name", "is_show", "sort").orderByAsc("sort");
        if (!Objects.isNull(sceneListDTO.getSpaceId()) && sceneListDTO.getSpaceId() > 0) {
            queryWrapper.eq("space_id", sceneListDTO.getSpaceId());
        }
        if (!Objects.isNull(sceneListDTO.getSceneId()) && sceneListDTO.getSceneId() > 0) {
            queryWrapper.ne("scene_id", sceneListDTO.getSceneId());
        }
        if (!Objects.isNull(sceneListDTO.getSceneName()) && !"".equals(sceneListDTO.getSceneName())) {
            queryWrapper.like("scene_name", "%" + sceneListDTO.getSceneName() + "%");
        }
        if (!Objects.isNull(sceneListDTO.getIsShow())) {
            queryWrapper.eq("is_show", sceneListDTO.getIsShow());
        }
        return (List<SceneListVO>) sceneMapper.selectList(queryWrapper).stream().map(po -> {
            SceneListVO vo = new SceneListVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 添加
     *
     * @param sceneAddDTO
     * @return
     */
    @Override
    public Integer add(SceneAddDTO sceneAddDTO) {
        String currentPath = System.getProperty("user.dir");
        String domain = DomainUtil.getCurrentDomain(request);
        String fileName = StringUtils.substringAfter(sceneAddDTO.getPanoramicImage(), domain);
        String absolutePath = new File(fileName).getAbsolutePath();
        // 执行生成命令
        try {
            if (SystemUtil.getOsInfo().isWindows()) {
                RuntimeUtil.execForStr(StrUtil.format("{}\\krpano\\win\\krpanotools64.exe makepano -config=templates\\vtour-vr.config {}", currentPath, absolutePath));
            } else {
                RuntimeUtil.execForStr(StrUtil.format("{}\\krpano\\linux\\krpanotools makepano -config=templates\\vtour-vr.config {}", currentPath, absolutePath));
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), 106);
        }
        // 设置一个随机值
        String randomString = StrUtil.format("scene_{}", IdUtil.simpleUUID());
        // 移动文件
        String vtourPath = StrUtil.format("{}\\vtour", StringUtils.substringBeforeLast(absolutePath, "\\"));
        String panosPath = StrUtil.format("{}\\panos", vtourPath);
        String materialFileName = FileUtil.ls(panosPath)[0].getName();
        FileUtil.copy(Paths.get(panosPath), Paths.get(new File(StrUtil.format("/static/scene/material/{}/", randomString)).getAbsolutePath()));
        // 加载xml
        try {
            // 读取
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(StrUtil.format("{}\\tour.xml", vtourPath)));
            //获取根节点
            Element rootElement = document.getRootElement();
            // 获取scene节点
            Element sceneEl = rootElement.element("scene");
            List<Element> images = sceneEl.elements("image");
            List<Element> levels = images.get(0).elements("level");
            List<LevelConfigBO> levelConfig = new ArrayList<>();
            for (int i = 0; i < levels.size(); i++) {
                LevelConfigBO levelConfigBO = new LevelConfigBO();
                levelConfigBO.setTiledimagewidth(levels.get(i).attribute("tiledimagewidth").getValue());
                levelConfigBO.setTiledimageheight(levels.get(i).attribute("tiledimageheight").getValue());
                levelConfigBO.setUrl(StringUtils.substringAfter(levels.get(i).element("cube").attribute("url").getValue(), StrUtil.format("panos/{}", materialFileName)));
                levelConfig.add(levelConfigBO);
            }
            // 存储数据库
            Scene insert = new Scene();
            BeanUtils.copyProperties(sceneAddDTO, insert);
            insert.setRandomString(randomString);
            insert.setMaterialFileName(materialFileName);
            insert.setXmlPath(StrUtil.format("/static/scene/{}", randomString + ".xml"));
            insert.setPanosPath(StrUtil.format("/static/scene/material/{}/", randomString));
            insert.setLevelConfig(JSON.toJSONString(levelConfig));
            insert.setControl(Control.DRAG);
            insert.setLimitview(Limitview.AUTO);
            insert.setHlookatmin("-360");
            insert.setHlookatmax("360");
            insert.setVlookatmin("-90");
            insert.setVlookatmax("90");
            return sceneMapper.insert(insert);
        } catch (Exception e) {
            FileUtil.del(vtourPath);
            throw new ServiceException(e.getMessage(), 106);
        }
    }

    /**
     * 详情
     *
     * @param scene_id
     * @return
     */
    @Override
    public SceneDetailVO detail(Integer scene_id) {
        Scene po = sceneMapper.selectById(scene_id);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        SceneDetailVO vo = new SceneDetailVO();
        BeanUtils.copyProperties(po, vo);
        vo.setLevelConfig(JSON.parseArray(po.getLevelConfig()));
        return vo;
    }

    @Override
    public String getXml(Integer sceneId) {
        SceneDetailVO vo = detail(sceneId);
        // 加载xml
        try {
            // 读取
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File("/static/baseXml/admin.xml").getAbsolutePath());
            //获取根节点
            Element rootElement = document.getRootElement();
            Element includeEl = rootElement.element("include");
            includeEl.addAttribute("url", StrUtil.format("/static/scene/{}", includeEl.attributeValue("url")));
            String materialPath = StrUtil.format("/static/scene/material/{}/panos/{}", vo.getRandomString(), vo.getMaterialFileName());
            //scene 标签
            Element sceneEl = rootElement.addElement("scene").addAttribute("name", vo.getRandomString()).addAttribute("title", vo.getSceneName()).addAttribute("onstart", "").addAttribute("havevrimage", "true").addAttribute("thumburl", StrUtil.format("{}/thumb.jpg", materialPath)).addAttribute("lat", "").addAttribute("lng", "").addAttribute("heading", "");
            //control标签
            sceneEl.addElement("control").addAttribute("mouse", vo.getControl().getKey()).addAttribute("touch", vo.getControl().getKey());
            //view标签
            Element viewEl = sceneEl.addElement("view").addAttribute("hlookat", vo.getHlookat()).addAttribute("vlookat", vo.getVlookat()).addAttribute("fovtype", "MFOV").addAttribute("fov", vo.getFov()).addAttribute("maxpixelzoom", "2.0").addAttribute("fovmin", "70").addAttribute("fovmax", "140").addAttribute("limitview", vo.getLimitview().getKey());
            if (vo.getLimitview().getKey() != "auto") {
                viewEl.addAttribute("hlookatmin", vo.getHlookatmin()).addAttribute("hlookatmax", vo.getHlookatmax()).addAttribute("vlookatmin", vo.getVlookatmin()).addAttribute("vlookatmax", vo.getVlookatmax());
            }
            //preview标签
            sceneEl.addElement("preview").addAttribute("url", StrUtil.format("{}/preview.jpg", materialPath));
            //image标签
            Element imageEl = sceneEl.addElement("image").addAttribute("type", "CUBE").addAttribute("multires", "true").addAttribute("tilesize", "512").addAttribute("if", "!webvr.isenabled");
            // level标签
            for (int i = 0; i < vo.getLevelConfig().size(); i++) {
                JSONObject jsonObject = vo.getLevelConfig().getJSONObject(i);
                imageEl.addElement("level").addAttribute("tiledimagewidth", jsonObject.getString("tiledimagewidth")).addAttribute("tiledimageheight", jsonObject.getString("tiledimageheight")).addElement("cube").addAttribute("url", materialPath + jsonObject.getString("url"));
            }
            //image标签
            sceneEl.addElement("image").addAttribute("if", "webvr.isenabled").addElement("cube").addAttribute("url", StrUtil.format("/vr/pano_%s.jpg", materialPath));
            return document.asXML();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), 106);
        }
    }

    /**
     * 编辑
     *
     * @param sceneEditDTO
     * @return
     */
    @Override
    public Integer edit(SceneEditDTO sceneEditDTO) {
        Scene po = sceneMapper.selectById(sceneEditDTO.getSceneId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Scene update = new Scene();
        BeanUtils.copyProperties(sceneEditDTO, update);
        return sceneMapper.updateById(update);
    }

    /**
     * 修改显示状态
     *
     * @param sceneId
     * @return
     */
    @Override
    public Integer editIsShow(Integer sceneId) {
        Scene po = sceneMapper.selectById(sceneId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return sceneMapper.update(new UpdateWrapper<Scene>().lambda().eq(Scene::getSceneId, sceneId).setSql("is_show = !is_show"));
    }

    /**
     * 修改排序
     *
     * @param sceneEditSortDTO
     * @return
     */
    @Override
    public Integer editSort(SceneEditSortDTO sceneEditSortDTO) {
        Scene po = sceneMapper.selectById(sceneEditSortDTO.getSceneId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Scene scene = new Scene();
        BeanUtils.copyProperties(sceneEditSortDTO, scene);
        return sceneMapper.updateById(scene);
    }

    /**
     * 删除
     *
     * @param sceneId
     * @return
     */
    @Override
    public Integer delete(Integer sceneId) {
        Scene po = sceneMapper.selectById(sceneId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return sceneMapper.deleteById(po);
    }
}




