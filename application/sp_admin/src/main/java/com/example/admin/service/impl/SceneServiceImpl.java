package com.example.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.bo.LevelConfigBO;
import com.example.admin.dto.*;
import com.example.admin.service.SceneService;
import com.example.admin.vo.SceneDetailVo;
import com.example.admin.vo.SceneListVo;
import com.example.admin.vo.ScenePaginateVo;
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
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
            ScenePaginateVo vo = new ScenePaginateVo();
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
    public List<SceneListVo> getList(SceneListDTO sceneListDTO) {
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
        return (List<SceneListVo>) sceneMapper.selectList(queryWrapper).stream().map(po -> {
            SceneListVo vo = new SceneListVo();
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
                RuntimeUtil.execForStr("ipconfig");
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
        FileUtil.copy(Paths.get(StrUtil.format("{}\\tour.xml", vtourPath)), Paths.get(new File(StrUtil.format("/scene/{}", randomString + ".xml")).getAbsolutePath()));
        FileUtil.copy(Paths.get(panosPath), Paths.get(new File(StrUtil.format("/scene/material/{}/", randomString)).getAbsolutePath()));
        // 加载xml
        try {
            // 读取
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(StrUtil.format("/scene/{}", randomString + ".xml")).getAbsolutePath());
            //获取skin_settings节点
            Element rootElement = document.getRootElement();
            //获取skin_settings节点
            Element skinSettingsEl = rootElement.element("skin_settings");
            //隐藏vr
            skinSettingsEl.addAttribute("webvr", "false");
            //隐藏title
            skinSettingsEl.addAttribute("title", "false");
            //隐藏缩略图
            skinSettingsEl.addAttribute("thumbs", "false");
            // 获取scene节点
            Element sceneEl = rootElement.element("scene");
            // 调整封面路径
            sceneEl.addAttribute("thumburl", StrUtil.format("material/{}/{}", randomString, sceneEl.attribute("thumburl")));
            //获取preview节点
            Element previewEl = sceneEl.element("preview");
            // 调整预览图片路径
            previewEl.addAttribute("url", StrUtil.format("material/{}/{}", randomString, sceneEl.attribute("url")));
            List<Element> images = sceneEl.elements("image");
            List<Element> levels = images.get(0).elements("level");
            List<LevelConfigBO> levelConfig = new ArrayList<>();
            for (int i = 0; i < levels.size(); i++) {
                String url = StrUtil.format("material/{}/{}", randomString, levels.get(i).element("cube").attribute("url").getValue());
                LevelConfigBO levelConfigBO = new LevelConfigBO();
                levelConfigBO.setTiledimagewidth(levels.get(i).attribute("tiledimagewidth").getValue());
                levelConfigBO.setTiledimageheight(levels.get(i).attribute("tiledimageheight").getValue());
                levelConfigBO.setUrl(StringUtils.substringAfter(levels.get(i).element("cube").attribute("url").getValue(), StrUtil.format("panos/{}", materialFileName)));
                levelConfig.add(levelConfigBO);
                levels.get(i).element("cube").addAttribute("url", url);
            }
            images.get(1).element("cube").addAttribute("url", StrUtil.format("material/{}/{}", randomString, images.get(1).element("cube").attribute("url").toString()));
            //添加拖拽热点action
            rootElement.element("action").addAttribute("name", "draghotspot").setText("spheretoscreen(ath, atv, hotspotcenterx, hotspotcentery, calc(mouse.stagex LT stagewidth/2 ? 'l' : 'r')); sub(drag_adjustx, mouse.stagex, hotspotcenterx); sub(drag_adjusty, mouse.stagey, hotspotcentery); asyncloop(pressed, sub(dx, mouse.stagex, drag_adjustx); sub(dy, mouse.stagey, drag_adjusty); screentosphere(dx, dy, ath, atv); js(listenerDragHotSpot(get('xml.scene'),get('name'),get(ath),get(atv))); );");
            //显示热点文字提醒
            rootElement.element("action").addAttribute("name", "show_tooltip").setText("txtadd(tooltipname, 'tooltip_', get(name)); addplugin(get(tooltipname)); txtadd(plugin[get(tooltipname)].parent, 'hotspot[', get(name), ']'); set(plugin[get(tooltipname)].url,'%SWFPATH%/plugins/textfield.swf'); set(plugin[get(tooltipname)].alpha,1); set(plugin[get(tooltipname)].align,top); set(plugin[get(tooltipname)].edge,bottom); set(plugin[get(tooltipname)].x,0); set(plugin[get(tooltipname)].y,-20); set(plugin[get(tooltipname)].autowidth,true); set(plugin[get(tooltipname)].autoheight,true); set(plugin[get(tooltipname)].vcenter,true); set(plugin[get(tooltipname)].background,true); set(plugin[get(tooltipname)].backgroundcolor,0x000000); set(plugin[get(tooltipname)].roundedge,5); set(plugin[get(tooltipname)].backgroundalpha,0.65); set(plugin[get(tooltipname)].css,'text-align:center; padding: 5px 10px; color:#FFFFFF; font-family:MicrosoftYahei; font-size:16px;'); copy(plugin[get(tooltipname)].html,tiptext)");
            //隐藏热点文字提示
            rootElement.element("action").addAttribute("name", "hide_tooltip").setText("txtadd(tooltipname, 'tooltip_', get(name)); set(plugin[get(tooltipname)].alpha,0);");
            //隐藏菜单
            rootElement.element("action").addAttribute("name", "hide_vtourskin").addAttribute("autorun", "onstart").setText("skin_hideskin('instant'); set(layer[skin_scroll_window].visible,false); set(layer[skin_control_bar].visible,false); set(layer[skin_btn_prev_fs].visible,false); set(layer[skin_btn_next_fs].visible,false);");
            // 保存
            Writer writer = new OutputStreamWriter(new FileOutputStream(new File(StrUtil.format("/scene/{}", randomString + ".xml")).getAbsolutePath()), "UTF-8");
            XMLWriter xmlWriter = new XMLWriter(writer);
            xmlWriter.write(document);
            xmlWriter.close();
            // 删除文件
            FileUtil.del(vtourPath);
            // 存储数据库
            Scene insert = new Scene();
            BeanUtils.copyProperties(sceneAddDTO, insert);
            insert.setRandomString(randomString);
            insert.setMaterialFileName(materialFileName);
            insert.setXmlPath(StrUtil.format("/scene/{}", randomString + ".xml"));
            insert.setPanosPath(StrUtil.format("/scene/material/{}/", randomString));
            insert.setLevelConfig(JSON.toJSONString(levelConfig));
            insert.setControl(Control.DRAG);
            insert.setLimitview(Limitview.AUTO);
            insert.setHlookatmin("180");
            insert.setHlookatmax("180");
            insert.setVlookatmin("-90");
            insert.setVlookatmax("90");
            return sceneMapper.insert(insert);
        } catch (Exception e) {
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
    public SceneDetailVo detail(Integer scene_id) {
        Scene po = sceneMapper.selectById(scene_id);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        SceneDetailVo vo = new SceneDetailVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
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




