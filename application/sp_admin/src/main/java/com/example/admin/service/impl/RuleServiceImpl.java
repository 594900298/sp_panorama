package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.RuleAddDTO;
import com.example.admin.dto.RuleEditDTO;
import com.example.common.mapper.RuleMapper;
import com.example.admin.service.RuleService;
import com.example.admin.vo.RuleDetailVO;
import com.example.admin.vo.RuleTreeVO;
import com.example.common.Interface.TreeNode;
import com.example.common.exception.ServiceException;
import com.example.common.po.RulePO;
import com.example.common.utils.AuthUtil;
import com.example.common.utils.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 规则
 */
@Service("adminRuleServiceImpl")
public class RuleServiceImpl extends ServiceImpl<RuleMapper, RulePO>
        implements RuleService {
    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private TreeUtil treeUtil;
    @Autowired
    private AuthUtil authUtil;

    /**
     * 获取树形列表
     *
     * @return
     */
    @Override
    public List<RuleTreeVO> getTree() {
        List<RulePO> list = ruleMapper.selectList(
                new QueryWrapper<RulePO>()
                        .select("rule_id", "pid", "name", "rule_url", "icon", "remark", "sort", "create_time")
                        .orderByAsc("sort")
        );
        // 构建树形节点
        List<TreeNode> treeNode = new ArrayList<>();
        Iterator<RulePO> it = list.iterator();
        while (it.hasNext()) {
            TreeNode i = it.next();
            treeNode.add(i);
        }
        //构建扩展属性
        HashMap<String, String> extraAttrList = new HashMap<>();
        extraAttrList.put("ruleUrl", "getRuleUrl");
        treeUtil.setIdKey("ruleId");
        treeUtil.setNameKey("name");
        treeUtil.setNodeList(treeNode);
        treeUtil.setExtraAttrList(extraAttrList);
        return RuleTreeVO.convertToVO(treeUtil.build());
    }

    /**
     * 添加
     *
     * @param ruleAddDTO
     * @return
     */
    @Override
    public Integer add(RuleAddDTO ruleAddDTO) {
        RulePO insert = new RulePO();
        BeanUtils.copyProperties(ruleAddDTO, insert);
        Integer res = ruleMapper.insert(insert);
        if (res != 0) {
            authUtil.clearRuleCache();
        }
        return res;
    }

    /**
     * 详情
     *
     * @param ruleId
     * @return
     */
    @Override
    public RuleDetailVO detail(Integer ruleId) {
        RulePO po = ruleMapper.selectOne(
                new QueryWrapper<RulePO>()
                        .select("rule_id", "pid", "name", "rule_url", "icon", "remark", "is_menu", "sort")
                        .lambda()
                        .eq(RulePO::getRuleId, ruleId)
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        RuleDetailVO vo = new RuleDetailVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 编辑
     *
     * @param ruleEditDTO
     * @return
     */
    @Override
    public Integer edit(RuleEditDTO ruleEditDTO) {
        RulePO po = ruleMapper.selectById(ruleEditDTO.getRuleId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        RulePO rulePO = new RulePO();
        BeanUtils.copyProperties(ruleEditDTO, rulePO);
        Integer res = ruleMapper.updateById(rulePO);
        if (res != 0) {
            authUtil.clearRuleCache();
        }
        return res;
    }

    /**
     * 删除
     *
     * @param ruleId
     * @return
     */
    @Override
    public Integer delete(Integer ruleId) {
        RulePO po = ruleMapper.selectById(ruleId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        po = ruleMapper.selectOne(
                new QueryWrapper<RulePO>()
                        .select("rule_id", "pid")
                        .lambda()
                        .eq(RulePO::getPid, ruleId.intValue())
        );
        if (!Objects.isNull(po)) {
            throw new ServiceException("存在子菜单无法删除~", 106);
        }
        int res = ruleMapper.deleteById(ruleId);
        if (res != 0) {
            authUtil.clearRuleCache();
        }
        return res;
    }
}

