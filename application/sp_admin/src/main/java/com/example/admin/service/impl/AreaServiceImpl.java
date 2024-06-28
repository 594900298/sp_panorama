package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.AreaAddDTO;
import com.example.admin.dto.AreaEditDTO;
import com.example.admin.service.AreaService;
import com.example.admin.vo.AreaDetailVO;
import com.example.admin.vo.AreaTreeVO;
import com.example.common.Interface.TreeNode;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.AreaMapper;
import com.example.common.po.Area;
import com.example.common.utils.RedisUtil;
import com.example.common.utils.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 地区
 */
@Service("adminAreaServiceImpl")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area>
        implements AreaService {
    /**
     * 缓存前缀
     */
    @Value("${app.common.appId}")
    private String prefix;

    /**
     * 管理员端缓存没有过滤隐藏
     */
    private String redisKey = "areaTree";

    /**
     * 客户端缓存过滤隐藏
     */
    private String apiRedisKey = "apiAreaTree";
    @Autowired
    private RedisTemplate redisTemplate = RedisUtil.redis;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TreeUtil treeUtil;
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 获取树形结构
     * @return
     */
    @Override
    public List<AreaTreeVO> getTree() {
        List<AreaTreeVO> returnData;
        // 查询缓存中是否存在数据
        if (redisUtil.isExpire(prefix + redisKey)) {
            QueryWrapper queryWrapper = new QueryWrapper<Area>().select("area_id", "pid", "area_name", "is_show", "sort", "area_key", "area_type", "create_time")
                    .orderByAsc("sort");
            List<Area> list = areaMapper.selectList(queryWrapper);
            // 构建树形节点
            List<TreeNode> treeNode = new ArrayList<>();
            Iterator<Area> it = list.iterator();
            while (it.hasNext()) {
                TreeNode i = it.next();
                treeNode.add(i);
            }
            //构建扩展属性
            HashMap<String, String> extraAttrList = new HashMap<>();
            extraAttrList.put("isShow", "getIsShow");
            extraAttrList.put("areaKey", "getAreaKey");
            extraAttrList.put("areaType", "getAreaType");
            treeUtil.setIdKey("areaId");
            treeUtil.setParentIdKey("pid");
            treeUtil.setNameKey("areaName");
            treeUtil.setNodeList(treeNode);
            treeUtil.setExtraAttrList(extraAttrList);
            List<AreaTreeVO> areaTreeVOList = AreaTreeVO.convertToVO(treeUtil.build());
            redisTemplate.opsForValue().set(prefix + redisKey, areaTreeVOList);
            returnData = areaTreeVOList;
        } else {
            returnData = (List<AreaTreeVO>) redisTemplate.opsForValue().get(prefix + redisKey);
        }
        return returnData;
    }

    /**
     * 添加区域
     * @param areaAddDTO
     * @return
     */
    @Override
    public Integer add(AreaAddDTO areaAddDTO) {
        Area insert = new Area();
        BeanUtils.copyProperties(areaAddDTO,insert);
        insert.setIsShow(true);
        insert.setSort(0);
        Integer res = areaMapper.insert(insert);
        if (res != 0) {
            redisTemplate.delete(prefix + redisKey);
            redisTemplate.delete(prefix + apiRedisKey);
        }
        return res;
    }

    /**
     * 获取详情
     *
     * @param areaId
     * @return
     */
    @Override
    public AreaDetailVO detail(Integer areaId) {
        Area data = areaMapper.selectOne(
                new QueryWrapper<Area>()
                        .select("area_id", "pid", "area_name", "area_key", "area_type")
                        .lambda()
                        .eq(Area::getAreaId, areaId)
        );
        if (data == null) {
            throw new ServiceException("找不到资源", 104);
        }
        AreaDetailVO vo = new AreaDetailVO();
        BeanUtils.copyProperties(data, vo);
        return vo;
    }

    /**
     * 修改
     * @param areaEditDTO
     * @return
     */
    @Override
    public Integer edit(AreaEditDTO areaEditDTO) {
        Area data = areaMapper.selectOne(
                new QueryWrapper<Area>()
                        .lambda()
                        .eq(Area::getAreaId, areaEditDTO.getAreaId())
        );
        if (data == null) {
            throw new ServiceException("找不到资源", 104);
        }
        Area area = new Area();
        BeanUtils.copyProperties(areaEditDTO,area);
        Integer res = areaMapper.updateById(area);
        if (res != 0) {
            redisTemplate.delete(prefix + redisKey);
            redisTemplate.delete(prefix + apiRedisKey);
        }
        return res;
    }

    /**
     * 修改显示状态
     *
     * @param areaId
     * @return
     */
    @Override
    public Integer editIsShow(Integer areaId) {
        Area data = areaMapper.selectOne(
                new QueryWrapper<Area>()
                        .lambda()
                        .eq(Area::getAreaId, areaId)
        );
        if (data == null) {
            throw new ServiceException("找不到资源", 104);
        }
        Integer res = areaMapper.update(
                new UpdateWrapper<Area>()
                        .lambda()
                        .eq(Area::getAreaId, areaId)
                        .setSql("is_show = !is_show")
        );
        if (res != 0) {
            redisTemplate.delete(prefix + redisKey);
            redisTemplate.delete(prefix + apiRedisKey);
        }
        return res;
    }

    /**
     * 删除
     *
     * @param areaId
     * @return
     */
    @Override
    public Integer delete(Integer areaId) {
        Area data = areaMapper.selectOne(
                new QueryWrapper<Area>()
                        .lambda()
                        .eq(Area::getAreaId, areaId)
        );
        if (data == null) {
            throw new ServiceException("找不到资源", 104);
        }
        Area children = areaMapper.selectOne(
                new QueryWrapper<Area>()
                        .lambda()
                        .eq(Area::getPid, areaId)
        );
        if (children != null) {
            throw new ServiceException("区域存在下级无法删除", 104);
        }
        /**
         * 删除
         */
        Integer res = areaMapper.delete(
                new QueryWrapper<Area>()
                        .lambda()
                        .eq(Area::getAreaId, areaId)
        );
        if (res != 0) {
            redisTemplate.delete(prefix + redisKey);
            redisTemplate.delete(prefix + apiRedisKey);
        }
        return res;
    }
}




