package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.admin.dto.LoginDTO;
import com.example.common.mapper.AdminMapper;
import com.example.admin.service.LoginService;
import com.example.admin.vo.LoginVO;
import com.example.common.exception.ServiceException;
import com.example.common.po.Admin;
import com.example.common.utils.CaptchaUtil;
import com.example.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 登录
 */
@Service("adminLoginServiceImpl")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired CaptchaUtil captchaUtil;

    @Value("${app.common.appSecret}")
    private String appSecret;



    /**
     * 管理员登录
     * @param loginDTO
     * @return
     * @throws Exception
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) throws Exception {
        //校验验证码
        boolean check = captchaUtil.checkCaptcha(loginDTO.getCodeSign(),loginDTO.getCode());
        if(!check){
            throw new ServiceException("验证码错误",106);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("user_id","username","uuid","status","login_time","login_ip","login_num");
        queryWrapper.eq("username",loginDTO.getUsername());
        queryWrapper.eq("password",DigestUtils.md5DigestAsHex((loginDTO.getPassword()+this.appSecret).getBytes()));
        Admin admin = adminMapper.selectOne(queryWrapper);
        if(admin != null){
            if(!admin.getStatus()){
                throw new ServiceException("当前用户已禁用",106);
            }else{
                // 更新
                Admin updateAdmin = new Admin();
                updateAdmin.setUserId(admin.getUserId());
                updateAdmin.setLoginTime((int) (System.currentTimeMillis()/1000));
                updateAdmin.setLoginNum(admin.getLoginNum()+1);
                updateAdmin.setLoginIp(request.getRemoteAddr());
                adminMapper.update(updateAdmin, new UpdateWrapper<Admin>().lambda().eq(Admin::getUserId,admin.getUserId()));
                HashMap<String, Object> token = tokenUtil.getToken(admin.getUserId(),admin.getUuid(),"admin");
                LoginVO vo = new LoginVO();
                vo.setToken((String) token.get("token"));
                vo.setRefreshToken((String) token.get("refreshToken"));
                vo.setExpire((Long) token.get("expire"));
                vo.setUsername(admin.getUsername());
                return vo;
            }
        }
        throw new ServiceException("用户名或密码错误",106);
    }

    /**
     * 刷新token
     * @param refreshToken
     * @param model
     * @return
     * @throws ServiceException
     */
    @Override
    public LoginVO refreshToken(String refreshToken, String model) throws ServiceException {
        HashMap<String, Object> token = tokenUtil.refreshToken(refreshToken,model);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken((String) token.get("token"));
        loginVO.setRefreshToken((String) token.get("refreshToken"));
        loginVO.setExpire((Long) token.get("expire"));
        loginVO.setUsername((String) token.get("username"));
        return loginVO;
    }
}
