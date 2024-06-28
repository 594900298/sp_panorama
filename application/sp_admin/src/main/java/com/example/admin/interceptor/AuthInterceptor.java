package com.example.admin.interceptor;

import com.alibaba.fastjson2.JSON;
import com.example.common.exception.ServiceException;
import com.example.common.po.ResultData;
import com.example.common.utils.AuthUtil;
import com.example.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Auth拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private AuthUtil authUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        //判断是否带有token
        System.out.println("请求地址" + request.getRequestURI());
        String token = request.getHeader("token");
        if (token == null) {
            try {
                throw new ServiceException("请先登录", -1);
            } catch (ServiceException e) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResultData.fail(-1, "请先登录")));
                return false;
            }
        }
        //验证token
        try {
            Map<String, Object> tokenInfo = tokenUtil.checkToken(token, "admin", false);
            //将token中的信息存放到session 共享
            request.getSession().setAttribute("model", "admin");
            request.getSession().setAttribute("id", tokenInfo.get("id"));
            request.getSession().setAttribute("info", tokenInfo);
            //权限验证
            authUtil.check(request.getRequestURI(), Integer.valueOf(String.valueOf(tokenInfo.get("id"))));
        } catch (ServiceException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(ResultData.fail(e.getCode(), e.getMessage())));
            return false;
        }
        return true;
    }
}
