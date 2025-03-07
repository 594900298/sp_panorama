package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GoodsCollectPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.GoodsCollect
 */
@Mapper
@Repository
public interface GoodsCollectMapper extends BaseMapper<GoodsCollectPO> {

}




