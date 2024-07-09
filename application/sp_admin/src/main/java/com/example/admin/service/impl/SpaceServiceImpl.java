package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.SpaceAddDTO;
import com.example.admin.dto.SpaceEditDTO;
import com.example.admin.dto.SpaceEditSortDTO;
import com.example.common.mapper.SpaceMapper;
import com.example.admin.service.SpaceService;
import com.example.admin.vo.SpaceDetailVO;
import com.example.admin.vo.SpaceListVO;
import com.example.admin.vo.SpacePaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.exception.ServiceException;
import com.example.common.po.Space;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_space(空间表
 * )】的数据库操作Service实现
 * @createDate 2024-06-25 11:10:11
 */
@Service
public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, Space>
        implements SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    /**
     * 分页
     *
     * @param pageParamBO
     * @return
     */
    @Override
    public IPage getPaginate(PageParamBO pageParamBO) {
        QueryWrapper queryWrapper = new QueryWrapper<Space>()
                .select("space_id", "space_id", "space_name", "space_thumb", "is_show", "sort")
                .orderByAsc("sort");
        // 查询数据
        return spaceMapper.selectPage(
                new Page<>(pageParamBO.getPageIndex(), pageParamBO.getPageSize()),
                queryWrapper
        ).convert(po -> {
            SpacePaginateVO vo = new SpacePaginateVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
    }

    /**
     * 列表
     *
     * @return
     */
    @Override
    public List<SpaceListVO> getList() {
        return spaceMapper.selectList(new QueryWrapper<Space>()
                .select("space_id", "space_id", "space_name", "space_thumb", "is_show", "sort")
                .lambda()
                .orderByAsc(Space::getSort)).stream().map(po -> {
            SpaceListVO vo = new SpaceListVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 添加
     *
     * @param spaceAddDTO
     * @return
     */
    @Override
    public Integer add(SpaceAddDTO spaceAddDTO) {
        Space insert = new Space();
        BeanUtils.copyProperties(spaceAddDTO, insert);
        insert.setSpaceCode(UUID.randomUUID().toString());
        return spaceMapper.insert(insert);
    }

    /**
     * 详情
     *
     * @param spaceId
     * @return
     */
    @Override
    public SpaceDetailVO detail(Integer spaceId) {
        Space po = spaceMapper.selectById(spaceId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        SpaceDetailVO vo = new SpaceDetailVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 编辑
     *
     * @param spaceEditDTO
     * @return
     */
    @Override
    public Integer edit(SpaceEditDTO spaceEditDTO) {
        Space po = spaceMapper.selectById(spaceEditDTO.getSpaceId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Space banner = new Space();
        BeanUtils.copyProperties(spaceEditDTO, banner);
        return spaceMapper.updateById(banner);
    }

    /**
     * 修改显示状态
     *
     * @param spaceId
     * @return
     */
    @Override
    public Integer editIsShow(Integer spaceId) {
        Space po = spaceMapper.selectById(spaceId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return spaceMapper.update(new UpdateWrapper<Space>().lambda().eq(Space::getSpaceId, spaceId).setSql("is_show = !is_show"));
    }

    /**
     * 修改排序
     *
     * @param spaceEditSortDTO
     * @return
     */
    @Override
    public Integer editSort(SpaceEditSortDTO spaceEditSortDTO) {
        Space po = spaceMapper.selectById(spaceEditSortDTO.getSpaceId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Space space = new Space();
        BeanUtils.copyProperties(spaceEditSortDTO, space);
        return spaceMapper.updateById(space);
    }

    /**
     * 删除
     *
     * @param spaceId
     * @return
     */
    @Override
    public Integer delete(Integer spaceId) {
        Space po = spaceMapper.selectById(spaceId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return spaceMapper.deleteById(po);
    }
}




