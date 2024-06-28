package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.BannerAddDTO;
import com.example.admin.dto.BannerEditDTO;
import com.example.admin.dto.BannerEditSortDTO;
import com.example.admin.dto.BannerPaginateDTO;
import com.example.admin.vo.BannerDetailVO;
import com.example.common.bo.PageParamBO;
import com.example.common.po.Banner;

/**
 *
 */
public interface BannerService extends IService<Banner> {
    IPage getPaginate(PageParamBO pageParamBO, BannerPaginateDTO BannerPaginateDTO);
    Integer add(BannerAddDTO bannerAddDTO);
    BannerDetailVO detail(Integer bannerId);
    Integer edit(BannerEditDTO bannerEditDTO);
    Integer editIsShow(Integer bannerId);
    Integer editSort(BannerEditSortDTO bannerEditSortDTO);
    Integer delete(Integer bannerId);
}
