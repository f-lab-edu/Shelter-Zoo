package com.noint.shelterzoo.config;

import com.noint.shelterzoo.config.interceptor.LoginCheckInterceptor;
import com.noint.shelterzoo.config.interceptor.SessionInjectionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final SessionInjectionInterceptor sessionInjectionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/sign-up", "/login", "/check/email/{email}", "/check/nickname/{nickname}");

        registry.addInterceptor(sessionInjectionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/sign-up", "/login", "/check/email/{email}", "/check/nickname/{nickname}");
    }
}
