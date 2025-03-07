package com.example.common.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.GroupAccessMapper;
import com.example.common.mapper.RuleMapper;
import com.example.common.po.RolePO;
import com.example.common.po.RulePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 权限管理类
 */
@Component
public class AuthUtil {
    @Autowired
    private RedisTemplate redisTemplate = RedisUtil.redis;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private GroupAccessMapper groupAccessMapper;
    @Value("${app.common.appId}")
    private String prefix; //缓存前缀
    private String authUserRule = "_auth_user_rule_"; //用户权限规则
    private String authRule = "_auth_rule";// 权限规则

    public AuthUtil() {
    }

    public AuthUtil(RedisTemplate redisTemplate, RedisUtil redisUtil, RuleMapper ruleMapper, GroupAccessMapper groupAccessMapper, String prefix, String authUserRule, String authRule) {
        this.redisTemplate = redisTemplate;
        this.redisUtil = redisUtil;
        this.ruleMapper = ruleMapper;
        this.groupAccessMapper = groupAccessMapper;
        this.prefix = prefix;
        this.authUserRule = authUserRule;
        this.authRule = authRule;
    }

    /**
     * 验证用户权限
     *
     * @param rule
     * @param id
     * @throws ServiceException
     */
    public void check(String rule, Integer id) throws ServiceException {
        //转成小写验证
        rule = rule.toLowerCase();
        //获取所有权限列表
        HashMap<String, String> authList = getAuthList();
        // 创建迭代器
        Iterator<Map.Entry<String, String>> authListIt = authList.entrySet().iterator();
        // 创建标识
        Boolean authListItFlag = false;
        while (!authListItFlag && authListIt.hasNext()) {
            Map.Entry<String, String> entry = authListIt.next();
            // 创建正则
            if(Pattern.matches(entry.getValue(), rule)){
                authListItFlag = true;
            }
        }
        //访问的接口在权限列表中，才需要验证
        if (authListItFlag) {
            List<String> userRule = getUserRule(id);
            // 获取迭代器
            Iterator<String> userRuleIt = userRule.iterator();
            // 创建标识
            Boolean userRuleItItFlag = false;
            while (!userRuleItItFlag && userRuleIt.hasNext()) {
                // 创建正则
                if(Pattern.matches(userRuleIt.next(), rule)){
                    userRuleItItFlag = true;
                }
            }
            if (!userRuleItItFlag) {
                throw new ServiceException("无权限访问", 105);
            }
        }
    }

    /**
     * 获取用户所有的权限列表
     *
     * @param id
     * @return
     */
    public List<String> getUserRule(Integer id) {
        List<String> returnData = new ArrayList<>();
        if (redisUtil.isExpire(prefix + authUserRule + id)) {
            //通过数据库将相关的查询出来
            List<RolePO> data = groupAccessMapper.selectUidRule(id);
            //获取所有权限列表
            HashMap<String, String> authList = getAuthList();
            Iterator<RolePO> it = data.iterator();
            while (it.hasNext()) {
                RolePO item = it.next();
                if ("*".equals(item.getRules())) {
                    //超级管理员
                    for (String v : authList.values()) {
                        //获取到值判断是否存在,存在插入
                        if (!returnData.contains(v)) {
                            returnData.add(v);
                        }
                    }
                } else {
                    List<String> rulesList = Arrays.asList(item.getRules().split(","));
                    for (String i : rulesList) {
                        if (authList.containsKey(Integer.parseInt(i)) && !returnData.contains(authList.get(Integer.parseInt(i)))) {
                            returnData.add(authList.get(Integer.parseInt(i)));
                        }
                    }
                }
            }
            redisTemplate.opsForValue().set(prefix + authUserRule + id, returnData);
        } else {
            returnData = JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(prefix + authUserRule + id)), ArrayList.class);
        }
        return returnData;
    }

    /**
     * 获取所有权限
     * 这个是为了减少数据库的查询
     *
     * @return
     */
    public HashMap<String, String> getAuthList() {
        HashMap<String, String> returnData;
        if (redisUtil.isExpire(prefix + authRule)) {
            //创建存储的map
            returnData = new HashMap<>();
            //获取数据库值
            List<RulePO> data = ruleMapper.selectList(new QueryWrapper<RulePO>().select("rule_id", "rule_url").lambda().orderByAsc(RulePO::getSort));
            Iterator<RulePO> it = data.iterator();
            //插入需要存储map
            while (it.hasNext()) {
                RulePO item = it.next();
                //转换到小写字母然后存入到数据库
                returnData.put(String.valueOf(item.getRuleId()), item.getRuleUrl().toLowerCase());
            }
            redisTemplate.opsForValue().set(prefix + authRule, returnData);
        } else {
            returnData = JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(prefix + authRule)), HashMap.class);
        }
        return returnData;
    }

    /**
     * 清除所有权限
     */
    public void clearRuleCache() {
        redisTemplate.delete(prefix + authRule);
        redisTemplate.delete(redisTemplate.keys(prefix + authUserRule + "*"));
    }

    /**
     * 清除某个用户的权限列表
     *
     * @param id
     */
    public void clearUserRuleCache(Integer id) {
        redisTemplate.delete(prefix + authUserRule + id);
    }
}
