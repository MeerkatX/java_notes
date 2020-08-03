package com.nuc.meerkatx.springbootstudy.config;

import com.nuc.meerkatx.springbootstudy.intercepter.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/31
 * @Description: 拦截器配置类 类似spring mvc中xml配置
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = new ArrayList<>();
        patterns.add("/hello/**");
        registry.addInterceptor(myInterceptor()).addPathPatterns(patterns);
    }
}
