package com.example.common.service.impl;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Interface.TreeNode;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.AreaMapper;
import com.example.common.po.Area;
import com.example.common.service.AreaService;
import com.example.common.utils.RedisUtil;
import com.example.common.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area>
        implements AreaService {

}




