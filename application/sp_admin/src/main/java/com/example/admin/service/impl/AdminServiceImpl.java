package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.*;
import com.example.common.mapper.AdminMapper;
import com.example.admin.service.AdminService;
import com.example.admin.vo.AdminDetailVO;
import com.example.admin.vo.AdminPaginateVO;
import com.example.common.bo.PageParamBO;
import com.example.common.exception.ServiceException;
import com.example.common.po.Admin;
import com.example.common.utils.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * 管理员
 */
@Service("adminAdminServiceImpl")
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {
    @Value("${app.common.appSecret}")
    private String appSecret;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 获取分页
     *
     * @param pageParamBO
     * @return
     */
    @Override
    public IPage getPaginate(PageParamBO pageParamBO) {
        // 查询数据
        return adminMapper.selectPage(
                new Page<>(pageParamBO.getPageIndex(), pageParamBO.getPageSize()),
                new QueryWrapper<Admin>()
                        .select("user_id", "username", "real_name", "mobile", "status", "is_main", "sort", "create_time")
                        .orderByDesc("user_id")
        ).convert(po -> {
            AdminPaginateVO v = new AdminPaginateVO();
            BeanUtils.copyProperties(po, v);
            return v;
        });
    }

    /**
     * 添加管理员
     *
     * @param adminAddDTO
     * @return
     */
    @Override
    public Integer add(AdminAddDTO adminAddDTO) {
        Admin data = adminMapper.selectOne(
                new QueryWrapper<Admin>().select("user_id").lambda().eq(Admin::getUsername, adminAddDTO.getUsername())
        );
        if (data != null) {
            throw new ServiceException("用户名已存在", 106);
        }
        Admin insert = new Admin();
        BeanUtils.copyProperties(adminAddDTO, insert);
        insert.setPassword(DigestUtils.md5DigestAsHex((adminAddDTO.getPassword() + this.appSecret).getBytes()));
        insert.setIsMain(false);
        insert.setSort(0);
        insert.setUuid(String.valueOf(UUID.randomUUID()));
        insert.setIsSystem(false);
        return adminMapper.insert(insert);
    }

    /**
     * 详情
     *
     * @param userId
     * @return
     */
    @Override
    public AdminDetailVO detail(Integer userId) {
        Admin po = adminMapper.selectOne(
                new QueryWrapper<Admin>()
                        .select("user_id", "username", "real_name", "mobile", "email", "status")
                        .lambda()
                        .eq(Admin::getUserId, userId)
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        AdminDetailVO vo = new AdminDetailVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 编辑
     *
     * @param adminEditDTO
     * @return
     */
    @Override
    public Integer edit(AdminEditDTO adminEditDTO) {
        Admin po = adminMapper.selectOne(
                new QueryWrapper<Admin>()
                        .select("user_id")
                        .lambda()
                        .eq(Admin::getUserId, adminEditDTO.getUserId())
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminEditDTO, admin);
        return adminMapper.updateById(admin);
    }

    /**
     * 更新状态
     *
     * @param userId
     * @return
     */
    @Override
    public Integer editStatus(Integer userId) {
        Admin po = adminMapper.selectById(userId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        if (po.getIsMain()) {
            throw new ServiceException("系统数据无法操作~", 106);
        }
        return adminMapper.update(new UpdateWrapper<Admin>().lambda().eq(Admin::getUserId, userId).setSql("status = !status"));
    }

    /**
     * 更新排序
     *
     * @param adminEditSortDTO
     * @return
     */
    @Override
    public Integer editSort(AdminEditSortDTO adminEditSortDTO) {
        Admin po = adminMapper.selectById(adminEditSortDTO.getUserId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        if (po.getIsMain()) {
            throw new ServiceException("系统数据无法操作~", 106);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminEditSortDTO, admin);
        return adminMapper.updateById(admin);
    }

    /**
     * 修改密码
     *
     * @param adminEditPasswordDTO
     * @return
     */
    @Override
    public Integer editPassword(AdminEditPasswordDTO adminEditPasswordDTO) {
        Admin po = adminMapper.selectById(adminEditPasswordDTO.getUserId());
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        Integer res = adminMapper.update(
                new UpdateWrapper<Admin>()
                        .lambda()
                        .eq(Admin::getUserId, adminEditPasswordDTO.getUserId())
                        .set(Admin::getPassword, DigestUtils.md5DigestAsHex((adminEditPasswordDTO.getPassword() + this.appSecret).getBytes())
                        )
        );
        //更新成功清空用户所有登录token
        if (res != 0) {
            tokenUtil.clearAllToken(po.getUuid());
        }
        return res;
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordDTO
     * @return
     */
    @Override
    public Integer modifyPassword(ModifyPasswordDTO modifyPasswordDTO) {
        if (!modifyPasswordDTO.getPassword().equals(modifyPasswordDTO.getConfirmPassword())) {
            throw new ServiceException("两次密码输入不一致", 104);
        }
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        Admin po = adminMapper.selectOne(new QueryWrapper<Admin>().lambda().eq(Admin::getUserId, userId));
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        if (!po.getPassword().equals(DigestUtils.md5DigestAsHex((modifyPasswordDTO.getOldPassword() + this.appSecret).getBytes()))) {
            throw new ServiceException("旧密码并不正确", 104);
        }
        int res = adminMapper.update(
                new UpdateWrapper<Admin>()
                        .lambda()
                        .eq(Admin::getUserId, userId)
                        .set(Admin::getPassword, DigestUtils.md5DigestAsHex((modifyPasswordDTO.getPassword() + this.appSecret).getBytes())
                        )
        );
        //更新成功清空用户所有登录token
        if (res != 0) {
            this.tokenUtil.clearAllToken(po.getUuid());
        }
        return res;
    }

    /**
     * 删除
     *
     * @param userId
     * @return
     */
    @Override
    public Integer delete(Integer userId) {
        Admin po = adminMapper.selectById(userId);
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        if (po.getIsMain()) {
            throw new ServiceException("系统数据无法删除~", 106);
        }
        String uuid = po.getUuid();
        int res = adminMapper.deleteById(userId);
        //更新成功清空用户所有登录token
        if (res != 0) {
            this.tokenUtil.clearAllToken(uuid);
        }
        return res;
    }
}
