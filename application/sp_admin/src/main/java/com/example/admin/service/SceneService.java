package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.*;
import com.example.admin.vo.SceneDetailVO;
import com.example.admin.vo.SceneListVO;
import com.example.common.bo.PageParamBO;
import com.example.common.po.Scene;

import java.util.List;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_scene(场景表)】的数据库操作Service
 * @createDate 2024-06-25 15:20:51
 */
public interface SceneService extends IService<Scene> {
    IPage getPaginate(PageParamBO pageParamBO, ScenePaginateDTO scenePaginateDTO);

    List<SceneListVO> getList(SceneListDTO sceneListDTO);

    Integer add(SceneAddDTO sceneAddDTO);

    SceneDetailVO detail(Integer sceneId);

    String getXml(Integer sceneId);

    Integer edit(SceneEditDTO sceneEditDTO);

    Integer editIsShow(Integer sceneId);

    Integer editSort(SceneEditSortDTO sceneEditSortDTO);

    Integer delete(Integer sceneId);
}
