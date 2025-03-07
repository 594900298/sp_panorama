package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.AreaAddDTO;
import com.example.admin.dto.AreaEditDTO;
import com.example.admin.vo.AreaDetailVO;
import com.example.admin.vo.AreaTreeVO;
import com.example.common.po.AreaPO;

import java.util.List;

public interface AreaService extends IService<AreaPO> {
    List<AreaTreeVO> getTree();
    Integer add(AreaAddDTO areaAddDTO);
    AreaDetailVO detail(Integer areaId);
    Integer edit(AreaEditDTO areaEditDTO);
    Integer editIsShow(Integer areaId);
    Integer delete(Integer areaId);
}
