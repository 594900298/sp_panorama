package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.vo.AreaTreeVO;
import com.example.common.po.Area;

import java.util.List;

public interface AreaService extends IService<Area> {
    List<AreaTreeVO> getTree();
}
