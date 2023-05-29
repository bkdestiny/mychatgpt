package com.mychatgpt.config;


import com.mychatgpt.util.LoginInterceptor;
import com.mychatgpt.util.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器 login
        registry.addInterceptor (new LoginInterceptor ()).excludePathPatterns (
                //放行的路径
                "/user/login"
        ).order (1);
        //添加拦截器 refreshToken
        registry.addInterceptor (new RefreshTokenInterceptor (stringRedisTemplate))
                .addPathPatterns ("/**")
                .order (0);
    }
}
