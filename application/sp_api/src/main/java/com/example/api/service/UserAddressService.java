package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.dto.UserAddressAddDTO;
import com.example.api.dto.UserAddressEditDTO;
import com.example.common.po.UserAddress;

import java.util.List;

/**
 *
 */
public interface UserAddressService extends IService<UserAddress> {
    List<UserAddressListVO> getList();

    Integer add(UserAddressAddDTO userAddressAddDTO);

    UserAddressDetailVO detail(Integer addressId);

    Integer edit(UserAddressEditDTO userAddressEditDTO);

    Integer editDefault(Integer addressId);

    Integer delete(Integer addressId);
}
