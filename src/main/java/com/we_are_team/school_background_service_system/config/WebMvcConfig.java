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
                .addResourceLocations("classpath:/static/", "classpath:/", "classpath:/templates/");

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /* 配置静态资源映射 */
        registry.addViewController("/").setViewName("forward:/user-login.html");
        registry.addViewController("/users/login").setViewName("forward:/user-login.html");
        registry.addViewController("/user/index").setViewName("forward:/user-index.html");
        registry.addViewController("/user/info").setViewName("forward:/user-info.html");
        registry.addViewController("/user/repair").setViewName("forward:/user-repair.html");
        registry.addViewController("/user/repair/item").setViewName("forward:/user-repair-item.html");
        registry.addViewController("/user/lostAndFound").setViewName("forward:/user-lostAndFound.html");
        registry.addViewController("/user/changeDormitory").setViewName("forward:/user-changeDormitory.html");
        registry.addViewController("/user/changeDormitory/item").setViewName("forward:/user-changeDormitory-item.html");
        registry.addViewController("/user/lostAndFound/item").setViewName("forward:/user-lostAndFound-item.html");
        registry.addViewController("/user/notice").setViewName("forward:/user-notice.html");
        registry.addViewController("/user/notice/item").setViewName("forward:/user-notice-item.html");
        registry.addViewController("/user/feedback").setViewName("forward:/user-feedback.html");
        registry.addViewController("/user/feedback/item").setViewName("forward:/user-feedback-item.html");

    }

}
