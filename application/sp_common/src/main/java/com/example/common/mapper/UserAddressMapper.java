package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.UserAddress
 */
@Mapper
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {

}




