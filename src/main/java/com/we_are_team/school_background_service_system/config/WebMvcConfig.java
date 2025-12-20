package com.we_are_team.school_background_service_system.config;

import com.we_are_team.school_background_service_system.interceptor.JwtTokenAdminInterceptor;
import com.we_are_team.school_background_service_system.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始进行拦截器配置");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");

        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
<<<<<<< Updated upstream
                .addResourceLocations("classpath:/static/", "classpath:/", "classpath:/templates/");


=======
                .addResourceLocations("classpath:/static/", "classpath:/templates/");
>>>>>>> Stashed changes
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /* 配置静态资源映射 */
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.addViewController("/login").setViewName("forward:/login.html");
        registry.addViewController("/index").setViewName("forward:/user-index.html");

    }

}
