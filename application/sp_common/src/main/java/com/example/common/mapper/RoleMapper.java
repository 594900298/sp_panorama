package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @description 针对表【sp_role(后台角色)】的数据库操作Mapper
 * @createDate 2023-07-23 20:11:49
 * @Entity com.example.common.model.Role
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}
