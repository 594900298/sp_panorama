package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.Order;
import com.example.common.po.OrderListPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.example.common.model.Order
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderListPO> autoCancelOrder(@Param("createTime") long createTime, @Param("orderStatus") String orderStatus);
}




