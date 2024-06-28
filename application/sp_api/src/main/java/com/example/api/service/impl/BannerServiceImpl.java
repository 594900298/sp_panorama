package com.example.api.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.service.BannerService;
import com.example.api.vo.BannerSelectVO;
import com.example.common.mapper.BannerMapper;
import com.example.common.po.Banner;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 轮播
 */
@Service("apiBannerServiceImpl")
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
        implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 列表
     *
     * @return
     */
    @Override
    public List<BannerSelectVO> getList() {
        return bannerMapper.selectList(new QueryWrapper<Banner>()
                .select("banner_id", "banner_name", "banner_image", "banner_link")
                .lambda()
                .eq(Banner::getIsShow, true)
                .orderByAsc(Banner::getSort)).stream().map(po -> {
            BannerSelectVO vo = new BannerSelectVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}




