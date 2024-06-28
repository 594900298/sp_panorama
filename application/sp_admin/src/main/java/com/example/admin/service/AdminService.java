package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.*;
import com.example.admin.vo.AdminDetailVO;
import com.example.common.bo.PageParamBO;
import com.example.common.po.Admin;

public interface AdminService extends IService<Admin> {
    IPage getPaginate(PageParamBO pageParamBO);
    Integer add(AdminAddDTO adminAddDTO);
    AdminDetailVO detail(Integer userId);
    Integer edit(AdminEditDTO adminEditDTO);
    Integer editStatus(Integer userId);
    Integer editSort(AdminEditSortDTO adminEditSortDTO);
    Integer editPassword(AdminEditPasswordDTO adminEditPasswordDTO);
    Integer modifyPassword(ModifyPasswordDTO modifyPasswordDTO);
    Integer delete(Integer userId);
}
