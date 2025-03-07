package com.example.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.mapper.RuleMapper;
import com.example.common.po.RulePO;
import com.example.common.service.RuleService;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, RulePO>
        implements RuleService {

}

