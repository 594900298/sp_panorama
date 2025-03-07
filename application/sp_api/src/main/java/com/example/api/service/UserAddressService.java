package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.dto.UserAddressAddDTO;
import com.example.api.dto.UserAddressEditDTO;
import com.example.common.po.UserAddressPO;

/**
 *
 */
public interface UserAddressService extends IService<UserAddressPO> {
    Integer add(UserAddressAddDTO userAddressAddDTO);

    Integer edit(UserAddressEditDTO userAddressEditDTO);

    Integer editDefault(Integer addressId);

    Integer delete(Integer addressId);
}
