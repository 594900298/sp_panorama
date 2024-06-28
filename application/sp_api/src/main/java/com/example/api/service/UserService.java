package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.po.User;

/**
 *
 */
public interface UserService extends IService<User> {
    UserCountVO getCount();
}
