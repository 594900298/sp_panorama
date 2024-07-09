package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.*;
import com.example.admin.vo.HotspotListVO;
import com.example.common.po.Hotspot;

import java.util.List;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_hotspot(热点表)】的数据库操作Service
 * @createDate 2024-07-02 09:23:17
 */
public interface HotspotService extends IService<Hotspot> {
    List<HotspotListVO> getList(HotspotListDTO hotspotListDTO);

    Integer add(HotspotAddDTO hotspotAddDTO);

    Integer edit(HotspotEditDTO hotspotEditDTO);

    Integer delete(Integer hotspotId);
}
