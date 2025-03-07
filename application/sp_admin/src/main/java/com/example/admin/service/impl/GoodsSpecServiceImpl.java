package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.mapper.GoodsSpecMapper;
import com.example.common.po.GoodsSpecPO;
import com.example.admin.service.GoodsSpecService;
import org.springframework.stereotype.Service;

/**
 * 规格
 */
@Service("adminGoodsSpecServiceImpl")
public class GoodsSpecServiceImpl extends ServiceImpl<GoodsSpecMapper, GoodsSpecPO>
    implements GoodsSpecService{

}




