package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.SpacePO;
import org.springframework.stereotype.Service;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Mapper
 * @createDate 2024-06-25 11:10:11
 * @Entity com.example.admin.domain.Space
 */
@Service
public interface SpaceMapper extends BaseMapper<SpacePO> {
}




