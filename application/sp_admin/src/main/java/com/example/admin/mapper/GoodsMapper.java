package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.po.GoodsDetailPO;
import com.example.common.po.GoodsPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Entity com.example.common.model.Goods
 */
@Mapper
@Repository("adminGoodsMapper")
public interface GoodsMapper extends BaseMapper<GoodsPO> {
    GoodsDetailPO getGoodsDetail(@Param("goodsId") Integer goodsId);
}




