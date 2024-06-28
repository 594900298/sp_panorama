package com.example.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.mapper.AdminMapper;
import com.example.common.po.Admin;
import com.example.common.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * 管理员
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {


}
