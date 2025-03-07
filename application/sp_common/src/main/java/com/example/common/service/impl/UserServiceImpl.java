package com.example.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.mapper.UserMapper;
import com.example.common.po.UserPO;
import com.example.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO>
        implements UserService {


}




