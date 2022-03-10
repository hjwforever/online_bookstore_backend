package com.springboot.online_bookstore_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowedHeaders("*");
    }

    // 将登录拦截器配置到容器中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/user/order/**",
                        "/payment/**",
                        "/role/**",
                        "/user/cart/**",
                        "/user/account/updatepassword",
                        "/user/account/userlist",
                        "/user/account/authorization",
                        "/user/address/**",
                        "/item/listNewBook",
                        "/item/delBook/**",
                        "/item/addCategory",
                        "/item/comment/reply",
                        "/item/comment/add",
                        "/user/account/bindemail",
                        "/user/account/getcaptcha")
                        .excludePathPatterns("/payment/success/**");
//                .excludePathPatterns("/item/home", "/user/account/login", "/user/account/register", "/user/account/logout", "/item/popular","/item/search","/item/detail");
//                .excludePathPatterns("/", "/index.html", "/login", "/register", "/css/**", "/js/**", "/img/**");
    }
}
