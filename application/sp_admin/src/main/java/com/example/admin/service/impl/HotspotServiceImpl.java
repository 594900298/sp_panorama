package com.example.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.HotspotAddDTO;
import com.example.admin.dto.HotspotEditDTO;
import com.example.admin.dto.HotspotListDTO;
import com.example.admin.service.HotspotService;
import com.example.admin.vo.HotspotListVO;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.HotspotMapper;
import com.example.common.po.Hotspot;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhenhuajiang
 * @description 针对表【sp_hotspot(热点表)】的数据库操作Service实现
 * @createDate 2024-07-02 09:23:17
 */
@Service("adminHotspotServiceImpl")
public class HotspotServiceImpl extends ServiceImpl<HotspotMapper, Hotspot>
        implements HotspotService {
    @Resource
    private HotspotMapper hotspotMapper;

    @Override
    public List<HotspotListVO> getList(HotspotListDTO hotspotListDTO) {
        QueryWrapper queryWrapper = new QueryWrapper<Hotspot>().select("hotspot_id", "scene_id", "hotspot_name", "random_string", "hotspot_style", "hotspot_type", "hotspot_value", "ath", "atv", "height", "width", "create_time").orderByAsc("hotspot_id");
        if (!Objects.isNull(hotspotListDTO.getSceneId()) && hotspotListDTO.getSceneId() > 0) {
            queryWrapper.eq("scene_id", hotspotListDTO.getSceneId());
        }
        return (List<HotspotListVO>) hotspotMapper.selectList(queryWrapper).stream().map(po -> {
            HotspotListVO vo = new HotspotListVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 添加
     *
     * @param hotspotAddDTO
     * @return
     */
    @Override
    public Integer add(HotspotAddDTO hotspotAddDTO) {
        Hotspot insert = new Hotspot();
        BeanUtils.copyProperties(hotspotAddDTO, insert);
        insert.setRandomString(StrUtil.format("hotspot_{}", IdUtil.simpleUUID()));
        return hotspotMapper.insert(insert);
    }

    /**
     * 修改
     *
     * @param hotspotEditDTO
     * @return
     */
    @Override
    public Integer edit(HotspotEditDTO hotspotEditDTO) {
        Hotspot po = hotspotMapper.selectById(hotspotEditDTO.getHotspotId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Hotspot hotspot = new Hotspot();
        BeanUtils.copyProperties(hotspotEditDTO, hotspot);
        return hotspotMapper.updateById(hotspot);
    }

    /**
     * 删除
     *
     * @param hotspotId
     * @return
     */
    @Override
    public Integer delete(Integer hotspotId) {
        Hotspot po = hotspotMapper.selectById(hotspotId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return hotspotMapper.deleteById(po);
    }
}




