package com.example.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.dto.UserAddressAddDTO;
import com.example.api.dto.UserAddressEditDTO;
import com.example.api.service.UserAddressService;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.UserAddressMapper;
import com.example.common.po.UserAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 */
@Service("apiUserAddressServiceImpl")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;

    @Resource
    TransactionDefinition transactionDefinition;

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<UserAddressListVO> getList() {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        return userAddressMapper.selectList(
                new QueryWrapper<UserAddress>()
                        .select("address_id", "province_id", "province_name", "city_id", "city_name", "district_id", "district_name", "name", "mobile", "full_address", "is_default")
                        .lambda()
                        .eq(UserAddress::getUserId, userId)
        ).stream().map(po -> {
            UserAddressListVO vo = new UserAddressListVO();
            BeanUtils.copyProperties(po, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 添加地址
     *
     * @param userAddressAddDTO
     * @return
     */
    @Override
    public Integer add(UserAddressAddDTO userAddressAddDTO) {
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
            // 将其它的改成非默认
            if (userAddressAddDTO.getIsDefault()) {
                userAddressMapper.update(new UpdateWrapper<UserAddress>().lambda().eq(UserAddress::getUserId, userId).set(UserAddress::getIsDefault, 0));
            }
            UserAddress insert = new UserAddress();
            BeanUtils.copyProperties(userAddressAddDTO, insert);
            insert.setUserId(userId);
            Integer result = userAddressMapper.insert(insert);
            // 提交
            dataSourceTransactionManager.commit(transactionStatus);
            return result;
        } catch (Exception e) {
            // 回滚
            dataSourceTransactionManager.rollback(transactionStatus);
            throw new ServiceException(e.getMessage(), 106);
        }
    }

    /**
     * 获取详情
     *
     * @param addressId
     * @return
     */
    @Override
    public UserAddressDetailVO detail(Integer addressId) {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        UserAddress po = userAddressMapper.selectOne(
                new QueryWrapper<UserAddress>()
                        .select("address_id", "province_id", "province_name", "city_id", "city_name", "district_id", "district_name", "name", "mobile", "full_address", "is_default")
                        .lambda()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getAddressId, addressId)
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        UserAddressDetailVO vo = new UserAddressDetailVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 更新收货地址
     *
     * @param userAddressEditDTO
     * @return
     */
    @Override
    public Integer edit(UserAddressEditDTO userAddressEditDTO) {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        UserAddress data = userAddressMapper.selectOne(
                new QueryWrapper<UserAddress>()
                        .select("address_id")
                        .lambda()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getAddressId, userAddressEditDTO.getAddressId())
        );
        if (Objects.isNull(data)) {
            throw new ServiceException("找不到资源", 104);
        }
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            // 将其它的改成非默认
            if (userAddressEditDTO.getIsDefault()) {
                userAddressMapper.update(new UpdateWrapper<UserAddress>().lambda().eq(UserAddress::getUserId, userId).set(UserAddress::getIsDefault, 0));
            }
            UserAddress po = new UserAddress();
            BeanUtils.copyProperties(userAddressEditDTO, po);
            Integer result = userAddressMapper.updateById(po);
            // 提交
            dataSourceTransactionManager.commit(transactionStatus);
            return result;
        } catch (Exception e) {
            // 回滚
            dataSourceTransactionManager.rollback(transactionStatus);
            throw new ServiceException(e.getMessage(), 106);
        }
    }

    /**
     * 修改默认
     *
     * @param addressId
     * @return
     */
    @Override
    public Integer editDefault(Integer addressId) {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        UserAddress po = userAddressMapper.selectOne(
                new QueryWrapper<UserAddress>()
                        .select("address_id")
                        .lambda()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getAddressId, addressId)
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            // 将其它的改成非默认
            userAddressMapper.update(
                    new UpdateWrapper<UserAddress>()
                            .lambda()
                            .ne(UserAddress::getAddressId, addressId)
                            .eq(UserAddress::getUserId, userId)
                            .set(UserAddress::getIsDefault, 0)
            );
            Integer result = userAddressMapper.update(
                    new UpdateWrapper<UserAddress>()
                            .lambda()
                            .eq(UserAddress::getAddressId, addressId)
                            .eq(UserAddress::getUserId, userId)
                            .set(UserAddress::getIsDefault, 1)
            );
            // 提交
            dataSourceTransactionManager.commit(transactionStatus);
            return result;
        } catch (Exception e) {
            // 回滚
            dataSourceTransactionManager.rollback(transactionStatus);
            throw new ServiceException(e.getMessage(), 106);
        }
    }

    /**
     * 删除收货地址
     *
     * @param addressId
     * @return
     */
    @Override
    public Integer delete(Integer addressId) {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        UserAddress po = userAddressMapper.selectOne(
                new QueryWrapper<UserAddress>()
                        .select("address_id")
                        .lambda()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getAddressId, addressId)
        );
        if (Objects.isNull(po)) {
            throw new ServiceException("找不到资源", 104);
        }
        return userAddressMapper.delete(
                new QueryWrapper<UserAddress>()
                        .lambda()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getAddressId, addressId));
    }
}




