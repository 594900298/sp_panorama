package com.example.admin.config;

import com.example.admin.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截Admin所有请求，通过判断token是否合法来决定是否需要登录
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/error",
                        "/admin/captcha/getCaptcha",
                        "/admin/login/code",
                        "/admin/login/refreshToken",
                        "/admin/scene/getXml/*"
                );
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

}
