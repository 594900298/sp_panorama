package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.RulePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @description 针对表【sp_rule(学校管理规则列表)】的数据库操作Mapper
 * @createDate 2023-07-22 23:52:47
 * @Entity com.example.common.model.Rule
 */
@Mapper
@Repository
public interface RuleMapper extends BaseMapper<RulePO> {

}
