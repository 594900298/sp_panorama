package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.RuleAddDTO;
import com.example.admin.dto.RuleEditDTO;
import com.example.admin.vo.RuleDetailVO;
import com.example.admin.vo.RuleTreeVO;
import com.example.common.po.Rule;

import java.util.List;

public interface RuleService extends IService<Rule> {
    List<RuleTreeVO> getTree();
    Integer add(RuleAddDTO ruleAddDTO);
    RuleDetailVO detail(Integer ruleId);
    Integer edit(RuleEditDTO ruleEditDTO);
    Integer delete(Integer ruleId);
}
