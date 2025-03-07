package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.SpaceAddDTO;
import com.example.admin.dto.SpaceEditDTO;
import com.example.admin.dto.SpaceEditSortDTO;
import com.example.admin.vo.SpaceDetailVO;
import com.example.admin.vo.SpaceListVO;
import com.example.common.bo.PageParamBO;
import com.example.common.po.SpacePO;

import java.util.List;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Service
 * @createDate 2024-06-25 11:10:11
 */
public interface SpaceService extends IService<SpacePO> {
    IPage getPaginate(PageParamBO pageParamBO);

    List<SpaceListVO> getList();

    Integer add(SpaceAddDTO spaceAddDTO);

    SpaceDetailVO detail(Integer spaceId);

    Integer edit(SpaceEditDTO spaceEditDTO);

    Integer editIsShow(Integer spaceId);

    Integer editSort(SpaceEditSortDTO spaceEditSortDTO);

    Integer delete(Integer spaceId);
}
