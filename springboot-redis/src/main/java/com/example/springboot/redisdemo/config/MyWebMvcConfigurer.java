package com.example.springboot.redisdemo.config;

import com.example.springboot.redisdemo.basic.CookieInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by liuguo on 2017/8/3.
 */
@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 这样来实例化bean可以实现自动注入
     * @return
     */
    @Bean
    public CookieInterceptor cookieInterceptor(){
        return new CookieInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cookieInterceptor())
                .excludePathPatterns("/toLogin","/users/login")
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 之前向下面这种写法,CookieInterceptor中的类不会依赖注入.
     */
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor())
                .excludePathPatterns("/toLogin","/users/login")
                .addPathPatterns("*//**");
        super.addInterceptors(registry);
    }*/

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toLogin").setViewName("login");
        super.addViewControllers(registry);
    }
}
