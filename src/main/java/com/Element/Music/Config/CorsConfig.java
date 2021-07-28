package com.Element.Music.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")//若需要指定接口或者排除接口在这里添加
                .allowedHeaders("*")
                .allowedMethods("POST", "GET")
                .allowedOrigins("*");//若需要指定域名或者排除域名在这里添加
    }
}