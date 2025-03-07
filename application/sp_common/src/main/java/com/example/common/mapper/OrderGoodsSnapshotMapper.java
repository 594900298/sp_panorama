package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.OrderGoodsSnapshotPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.OrderGoodsSnapshot
 */
@Mapper
@Repository
public interface OrderGoodsSnapshotMapper extends BaseMapper<OrderGoodsSnapshotPO> {

}




