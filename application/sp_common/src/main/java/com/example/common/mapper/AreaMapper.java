package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.AreaPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.Area
 */
@Mapper
@Repository
public interface AreaMapper extends BaseMapper<AreaPO> {
}




