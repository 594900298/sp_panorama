package com.example.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.api.vo.BannerSelectVO;
import com.example.common.po.BannerPO;

import java.util.List;

/**
 *
 */
public interface BannerService extends IService<BannerPO> {
    List<BannerSelectVO> getList();
}
