package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.User
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserPO> {

}




