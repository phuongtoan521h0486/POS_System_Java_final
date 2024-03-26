package com.thd.pos_system_java_final.conf;

import com.thd.pos_system_java_final.shared.ultils.RoleInterceptor;
import com.thd.pos_system_java_final.shared.ultils.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebProtectConfig implements WebMvcConfigurer {
    @Autowired
    RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/user/**");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/cart/**");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/product/**").addPathPatterns("/customer/**");

        registry.addInterceptor(roleInterceptor).addPathPatterns("/user/**")
                .excludePathPatterns("/user/dashboard")
                .excludePathPatterns("/user/profile")
                .addPathPatterns("/product/*/*")
                .addPathPatterns("/customer/edit/**")
                .addPathPatterns("/customer/delete/**");
    }
}
