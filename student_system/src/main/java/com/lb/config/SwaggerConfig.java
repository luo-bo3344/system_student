package com.lb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
/**
 * swagger的配置类用于测试后端代码
 * */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    //创建swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("毕业设计")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lb.controller"))
                .build();
    }

    //创建swagger的相关作者信息
    public ApiInfo apiInfo(){
        Contact contact = new Contact("luobo", "", "2235849253@qq.com");
        return new ApiInfo("罗波毕业设计的API文档",
                "毕业设计一切顺利",
                "1.0",
                "http://116.62.17.193:8080/route/login1",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
