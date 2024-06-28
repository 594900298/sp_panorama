package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.GroupAccess;
import com.example.common.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @description 针对表【sp_group_access】的数据库操作Mapper
 * @createDate 2023-07-23 16:46:01
 * @Entity com.example.common.model.GroupAccess
 */
@Mapper
@Repository
public interface GroupAccessMapper extends BaseMapper<GroupAccess> {
    List<Role> selectUidRule(Integer id);
}
