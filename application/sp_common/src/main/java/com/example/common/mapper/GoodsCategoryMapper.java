package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.GoodsCategory
 */
@Mapper
@Repository
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

}




