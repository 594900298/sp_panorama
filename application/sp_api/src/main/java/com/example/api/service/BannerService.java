package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.vo.BannerSelectVO;
import com.example.common.po.Banner;

import java.util.List;

/**
 *
 */
public interface BannerService extends IService<Banner> {
    List<BannerSelectVO> getList();
}
