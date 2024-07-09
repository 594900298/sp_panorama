package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.vo.SpaceDetailVO;
import com.example.common.po.Space;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Service
 * @createDate 2024-06-25 11:10:11
 */
public interface SpaceService extends IService<Space> {
    SpaceDetailVO detail(Integer spaceId);

    String getXml(Integer spaceId);
}
