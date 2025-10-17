package com.example.api.service.impl;

import com.example.api.service.LoginService;
import com.example.api.vo.LoginVO;
import com.example.common.exception.ServiceException;
import com.example.common.mapper.UserMapper;
import com.example.common.utils.CaptchaUtil;
import com.example.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


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
