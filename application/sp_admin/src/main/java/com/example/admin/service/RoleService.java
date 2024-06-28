package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.RoleAddDTO;
import com.example.admin.dto.RoleEditDTO;
import com.example.admin.dto.RoleEditSortDTO;
import com.example.admin.vo.RoleDetailVO;
import com.example.admin.vo.RolePaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.po.Role;

public interface RoleService extends IService<Role> {
    IPage<RolePaginateVO> getPaginate(PageParamBO pageParamBO);
    Integer add(RoleAddDTO roleAddDTO);
    RoleDetailVO detail(Integer roleId);
    Integer edit(RoleEditDTO roleEditDTO);
    Integer editSort(RoleEditSortDTO roleEditSortDTO);
    Integer delete(Integer roleId);
}
