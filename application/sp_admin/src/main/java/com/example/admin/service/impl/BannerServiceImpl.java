package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.BannerAddDTO;
import com.example.admin.dto.BannerEditDTO;
import com.example.admin.dto.BannerEditSortDTO;
import com.example.admin.dto.BannerPaginateDTO;
import com.example.admin.service.BannerService;
import com.example.admin.vo.BannerDetailVO;
import com.example.admin.vo.BannerPaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.BannerMapper;
import com.example.common.po.BannerPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 轮播
 */
@Service("adminBannerServiceImpl")
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerPO>
        implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 分页查询
     *
     * @param pageParamBO
     * @param bannerPaginateDTO
     * @return
     */
    @Override
    public IPage getPaginate(PageParamBO pageParamBO, BannerPaginateDTO bannerPaginateDTO) {
        QueryWrapper queryWrapper = new QueryWrapper<BannerPO>()
                .select("banner_id", "banner_name", "banner_image", "banner_link", "is_show", "sort")
                .orderByAsc("sort");
        if (!Objects.isNull(bannerPaginateDTO.getIsShow())) {
            queryWrapper.eq("is_show", bannerPaginateDTO.getIsShow());
        }
        // 查询数据
        return bannerMapper.selectPage(
                new Page<>(pageParamBO.getPageIndex(), pageParamBO.getPageSize()),
                queryWrapper
        ).convert(po -> {
            BannerPaginateVO vo = new BannerPaginateVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
    }

    /**
     * 添加轮播图
     *
     * @param bannerAddDTO
     * @return
     */
    @Override
    public Integer add(BannerAddDTO bannerAddDTO) {
        BannerPO insert = new BannerPO();
        BeanUtils.copyProperties(bannerAddDTO, insert);
        return bannerMapper.insert(insert);
    }

    /**
     * 获取详情
     *
     * @param bannerId
     * @return
     */
    @Override
    public BannerDetailVO detail(Integer bannerId) {
        BannerPO po = bannerMapper.selectById(bannerId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        BannerDetailVO vo = new BannerDetailVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 编辑
     *
     * @param bannerEditDTO
     * @return
     */
    @Override
    public Integer edit(BannerEditDTO bannerEditDTO) {
        BannerPO po = bannerMapper.selectById(bannerEditDTO.getBannerId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        BannerPO bannerPO = new BannerPO();
        BeanUtils.copyProperties(bannerEditDTO, bannerPO);
        return bannerMapper.updateById(bannerPO);
    }

    /**
     * 更新显示状态
     *
     * @param bannerId
     * @return
     */
    @Override
    public Integer editIsShow(Integer bannerId) {
        BannerPO po = bannerMapper.selectById(bannerId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return bannerMapper.update(new UpdateWrapper<BannerPO>().lambda().eq(BannerPO::getBannerId, bannerId).setSql("is_show = !is_show"));
    }

    /**
     * 更新排序
     *
     * @param bannerEditSortDTO
     * @return
     */
    @Override
    public Integer editSort(BannerEditSortDTO bannerEditSortDTO) {
        BannerPO po = bannerMapper.selectById(bannerEditSortDTO.getBannerId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        BannerPO bannerPO = new BannerPO();
        BeanUtils.copyProperties(bannerEditSortDTO, bannerPO);
        return bannerMapper.updateById(bannerPO);
    }

    /**
     * 删除
     *
     * @param bannerId
     * @return
     */
    @Override
    public Integer delete(Integer bannerId) {
        BannerPO po = bannerMapper.selectById(bannerId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return bannerMapper.delete(new QueryWrapper<BannerPO>().lambda().eq(BannerPO::getBannerId, bannerId));
    }


}




