package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GoodsAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.GoodsAttrValue
 */
@Mapper
@Repository
public interface GoodsAttrValueMapper extends BaseMapper<GoodsAttrValue> {

}




