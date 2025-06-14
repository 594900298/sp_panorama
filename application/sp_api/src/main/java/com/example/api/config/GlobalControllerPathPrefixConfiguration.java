package com.example.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 为 Controller 接口配置统一前缀
 */
@Configuration
public class GlobalControllerPathPrefixConfiguration implements WebMvcConfigurer {
    @Autowired
    private WebProperties webProperties;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 根据不同包匹配表达式，添加各自的统一前缀
        configurePathMatch(configurer, webProperties.getApi());
    }

    /**
     * API 前缀：实现指定的controller 提供的 RESTFul API 的统一前缀
     * <p>
     * 意义：通过该前缀，避免Swagger,Actuator 意外通过Nginx暴露出来给外部，带来安全性问题
     * 这样Nginx只需配置转发到 指定统一前缀 的所有接口即可
     *
     * @param configurer
     * @param api
     * @see AntPathMatcher
     */
    private void configurePathMatch(PathMatchConfigurer configurer, WebProperties.Api api) {
        // 创建路径匹配类，指定以'.'分隔
        AntPathMatcher antPathMatcher = new AntPathMatcher(".");
        // 指定匹配前缀
        // 满足：类上有RestController注解 && 该类的包名匹配指定的自定义包的表达式
        configurer.addPathPrefix(api.getPrefix(), clazz -> clazz.isAnnotationPresent(RestController.class)
                && antPathMatcher.match(api.getControllerPath(), clazz.getPackage().getName()));
    }
}
