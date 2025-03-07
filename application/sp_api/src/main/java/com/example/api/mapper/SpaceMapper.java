package com.example.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.api.po.SpaceDetailPO;
import com.example.common.po.SpacePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Mapper
 * @createDate 2024-06-25 11:10:11
 * @Entity com.example.admin.domain.Space
 */
@Mapper
@Repository("apiSpaceMapper")
public interface SpaceMapper extends BaseMapper<SpacePO> {
    SpaceDetailPO selectJoinById(Integer id);
}




