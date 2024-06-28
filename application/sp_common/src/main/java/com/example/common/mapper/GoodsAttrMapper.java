package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GoodsAttr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.GoodsAttr
 */
@Mapper
@Repository
public interface GoodsAttrMapper extends BaseMapper<GoodsAttr> {

}




