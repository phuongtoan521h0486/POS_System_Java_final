package com.thd.pos_system_java_final.conf;

import com.thd.pos_system_java_final.ultils.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebProtectConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/user/**");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/cart/**");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/product/**");
    }
}
