package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GoodsSpecPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.GoodsSpec
 */
@Mapper
@Repository
public interface GoodsSpecMapper extends BaseMapper<GoodsSpecPO> {

}




