package com.example.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.mapper.RoleMapper;
import com.example.common.po.Role;
import com.example.common.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 管理员
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

}
