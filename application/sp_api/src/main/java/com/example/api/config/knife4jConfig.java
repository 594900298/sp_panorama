package com.example.api.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@Slf4j
@EnableSwagger2
@EnableKnife4j
@EnableWebMvc
public class knife4jConfig implements WebMvcConfigurer {
    @Value("${knife4j.enable}")
    private String enable;

    /**
     * @description:设置静态资源映射(静态资源不放到static中需要进行配置)
     * @param: [registry]
     * @return: void
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","file:static/");
    }

    /**
     * api文档
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .enable(Boolean.parseBoolean(enable))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.api"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 文档信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档标题")
                .version("v1.0.0")
                .description("山内有樱名为良，树本无名只待春")
                .contact(new Contact("zhenhuajiang", "", "594900298@qq.com"))
                .build();
    }
}
