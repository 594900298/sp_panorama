package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.RoleAddDTO;
import com.example.admin.dto.RoleEditDTO;
import com.example.admin.dto.RoleEditSortDTO;
import com.example.admin.service.RoleService;
import com.example.admin.vo.RoleDetailVO;
import com.example.admin.vo.RolePaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.RoleMapper;
import com.example.common.po.RolePO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 管理员
 */
@Service("adminRoleServiceImpl")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO>
        implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页
     *
     * @param pageParamBO
     * @return
     */
    @Override
    public IPage getPaginate(PageParamBO pageParamBO) {
        // 查询数据
        return roleMapper.selectPage(
                new Page<>(pageParamBO.getPageIndex(), pageParamBO.getPageSize()),
                new QueryWrapper<RolePO>()
                        .select("role_id", "role_name", "sort", "is_system")
                        .orderByAsc("sort")
        ).convert(po -> {
            RolePaginateVO vo = new RolePaginateVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
    }

    /**
     * 添加
     *
     * @param roleAddDTO
     * @return
     */
    @Override
    public Integer add(RoleAddDTO roleAddDTO) {
        RolePO insert = new RolePO();
        BeanUtils.copyProperties(roleAddDTO, insert);
        insert.setRules(String.join(",", roleAddDTO.getRules().stream().map(Object::toString).collect(Collectors.toList())));
        insert.setSort(0);
        insert.setIsSystem(false);
        return roleMapper.insert(insert);
    }

    /**
     * 详情
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleDetailVO detail(Integer roleId) {
        RolePO po = roleMapper.selectOne(
                new QueryWrapper<RolePO>()
                        .select("role_id", "role_name", "role_desc", "rules", "is_show", "sort", "is_system")
                        .lambda()
                        .eq(RolePO::getRoleId, roleId)
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        RoleDetailVO vo = new RoleDetailVO();
        BeanUtils.copyProperties(po, vo);
        vo.setRules(Arrays.asList(po.getRules().split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()));
        return vo;
    }

    /**
     * 更新
     *
     * @param roleEditDTO
     * @return
     */
    @Override
    public Integer edit(RoleEditDTO roleEditDTO) {
        RolePO po = roleMapper.selectById(roleEditDTO.getRoleId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        RolePO rolePO = new RolePO();
        rolePO.setRules(String.join(",", roleEditDTO.getRules().stream().map(Object::toString).collect(Collectors.toList())));
        BeanUtils.copyProperties(roleEditDTO, rolePO);
        return roleMapper.updateById(rolePO);
    }

    /**
     * 修改排序
     *
     * @param roleEditSortDTO
     * @return
     */
    @Override
    public Integer editSort(RoleEditSortDTO roleEditSortDTO) {
        RolePO po = roleMapper.selectById(roleEditSortDTO.getRoleId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        RolePO rolePO = new RolePO();
        BeanUtils.copyProperties(roleEditSortDTO, rolePO);
        return roleMapper.updateById(rolePO);
    }

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    @Override
    public Integer delete(Integer roleId) {
        RolePO po = roleMapper.selectById(roleId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        if (po.getIsSystem()) {
            throw new ServiceException("系统数据无法删除~", 106);
        }
        return roleMapper.deleteById(roleId);
    }

}
