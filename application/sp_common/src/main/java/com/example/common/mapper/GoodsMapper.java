package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GoodsPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @Entity com.example.common.model.Goods
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<GoodsPO> {

}




