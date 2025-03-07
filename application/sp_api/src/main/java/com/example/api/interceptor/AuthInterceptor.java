package com.example.api.interceptor;

import com.alibaba.fastjson2.JSON;
import com.example.common.exception.ServiceException;
import com.example.common.vo.ResultDataVO;
import com.example.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (token == null) {
            try {
                throw new ServiceException("请先登录", -1);
            } catch (ServiceException e) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResultDataVO.fail(-1,"请先登录")));
                return false;
            }
        }
        //验证token
        try {
            Map<String, Object> tokenInfo = tokenUtil.checkToken(token,"api", false);
            //将token中的信息存放到session 共享
            request.getSession().setAttribute("model","api");
            request.getSession().setAttribute("id",tokenInfo.get("id"));
            request.getSession().setAttribute("openid",tokenInfo.get("openid"));
        } catch (ServiceException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(ResultDataVO.fail(e.getCode(),e.getMessage())));
            return false;
        }
        return true;
    }
}
