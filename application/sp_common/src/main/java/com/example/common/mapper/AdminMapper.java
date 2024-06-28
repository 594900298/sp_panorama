package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 管理员
 *
 * @Entity com.example.common.model.Admin
 */
@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

}




