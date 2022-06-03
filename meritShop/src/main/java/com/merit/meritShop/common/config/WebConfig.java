package com.merit.meritShop.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

   /* @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath)

                //  .addResourceLocations("classpath:/static/images")
                .setCachePeriod(60);

        registry.addResourceHandler("/lib/**")
                .addResourceLocations("classpath:/static/lib");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js");
        registry.addResourceHandler("/scss/**")
                .addResourceLocations("classpath:/static/scss");
        registry.addResourceHandler("/mail/**")
                .addResourceLocations("classpath:/static/mail");
        registry.addResourceHandler("/scripts/**")
                .addResourceLocations("classpath:/static/scripts");

    }
*/

}