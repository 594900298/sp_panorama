package com.example.api.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.api.dto.LoginCodeDTO;
import com.example.common.mapper.UserMapper;
import com.example.api.service.LoginService;
import com.example.api.vo.LoginVO;
import com.example.common.exception.ServiceException;
import com.example.common.po.User;
import com.example.common.utils.CaptchaUtil;
import com.example.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 登录
 */
@Service("apiLoginServiceImpl")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    CaptchaUtil captchaUtil;

    @Value("${app.common.appSecret}")
    private String appSecret;

    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;

    @Resource
    TransactionDefinition transactionDefinition;

    /**
     * 微信登录
     *
     * @param loginCodeDTO
     * @return
     * @throws Exception
     */
    @Override
    public LoginVO code(LoginCodeDTO loginCodeDTO) throws Exception {
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            User user = userMapper.selectOne(
                    new QueryWrapper<User>()
                            .select("user_id", "status", "uuid", "openid")
                            .lambda()
            );
            Map<String, Object> resMap = new HashMap<>();
            if (user != null) {
                if (!user.getStatus()) {
                    throw new ServiceException("当前用户已禁用", 106);
                }
                resMap.put("userId", user.getUserId());
                resMap.put("uuid", user.getUuid());
                resMap.put("name", user.getName());
            } else {
                /**
                 * 插入新用户
                 */
                User insertData = new User();
                insertData.setStatus(true);
                insertData.setUuid(String.valueOf(UUID.randomUUID()));
                userMapper.insert(insertData);
                resMap.put("userId", insertData.getUserId());
                resMap.put("uuid", insertData.getUuid());
                resMap.put("name", null);
            }
            // 更新
            userMapper.update(
                    new UpdateWrapper<User>()
                            .lambda()
                            .set(User::getLoginTime, System.currentTimeMillis() / 1000)
                            .set(User::getLoginIp, request.getRemoteAddr())
                            .setSql("login_num = login_num+1 ")
            );
            Map<String, Object> data = new HashMap<>();
            //这些数据不会返回给用户
            tokenUtil.setRedisData(data);
            HashMap<String, Object> token = tokenUtil.getToken((Integer) resMap.get("userId"), (String) resMap.get("uuid"), "api");
            dataSourceTransactionManager.commit(transactionStatus);
            LoginVO vo = new LoginVO();
            vo.setToken((String) token.get("token"));
            vo.setRefreshToken((String) token.get("refreshToken"));
            vo.setExpire((Long) token.get("expire"));
            vo.setName((String) resMap.get("name"));
            return vo;
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            throw new ServiceException(e.getMessage(), 106);
        }
    }

    /**
     * 刷新token
     *
     * @param refreshToken
     * @param model
     * @return
     * @throws ServiceException
     */
    @Override
    public LoginVO refreshToken(String refreshToken, String model) throws ServiceException {
        HashMap<String, Object> token = tokenUtil.refreshToken(refreshToken, model);
        LoginVO vo = new LoginVO();
        vo.setToken((String) token.get("token"));
        vo.setRefreshToken((String) token.get("refreshToken"));
        vo.setExpire((Long) token.get("expire"));
        vo.setName((String) token.get("name"));
        vo.setOpenid((String) token.get("openid"));
        return vo;
    }
}
