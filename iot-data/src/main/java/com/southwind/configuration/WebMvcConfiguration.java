package com.southwind.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Value("${upload.numberUrl}")
    String numberUrl;
    @Value("${upload.excel}")
    String excel;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/park/upload/number/**").addResourceLocations("file:"+numberUrl);
        registry.addResourceHandler("/park/upload/excel/**").addResourceLocations("file:"+excel);
    }
}
