package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.po.OrderDetailPO;
import com.example.common.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.admin.model.Order
 */
@Mapper
@Repository("adminOrderMapper")
public interface OrderMapper extends BaseMapper<Order> {
    OrderDetailPO getDetailJoinById(Integer orderId);
}




