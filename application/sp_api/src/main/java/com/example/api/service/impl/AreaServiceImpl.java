package com.example.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.vo.AreaTreeVO;
import com.example.common.mapper.AreaMapper;
import com.example.api.service.AreaService;
import com.example.common.Interface.TreeNode;
import com.example.common.po.AreaPO;
import com.example.common.utils.RedisUtil;
import com.example.common.utils.TreeUtil;
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
@Service("apiAreaServiceImpl")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, AreaPO>
        implements AreaService {
    /**
     * 缓存前缀
     */
    @Value("${app.common.appId}")
    private String prefix;

    /**
     * 管理员端缓存没有过滤隐藏
     */
    private String redisKey = "apiAreaTree";

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
     *
     * @return
     */
    @Override
    public List<AreaTreeVO> getTree() {
        List<AreaTreeVO> returnData;
        // 查询缓存中是否存在数据
        if (redisUtil.isExpire(prefix + redisKey)) {
            List<AreaPO> list = areaMapper.selectList(
                    new QueryWrapper<AreaPO>()
                            .select("area_id", "pid", "area_name", "sort", "area_key", "area_type", "create_time")
                            .lambda()
                            .eq(AreaPO::getIsShow, true)
                            .orderByAsc(AreaPO::getSort)
            );
            // 构建树形节点
            List<TreeNode> treeNode = new ArrayList<>();
            Iterator<AreaPO> it = list.iterator();
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
}




