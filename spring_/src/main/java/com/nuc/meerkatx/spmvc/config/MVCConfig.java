package com.nuc.meerkatx.spmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @ClassName: Config
 * @Auther: MeerkatX
 * @Date: 2020-08-05 20:38
 * @Description:
 */
@Configuration
@EnableWebMvc//启动spring mvc
@ComponentScan("com.nuc.meerkatx.spmvc")//启动组件扫描
public class MVCConfig implements WebMvcConfigurer {

    //jsp视图解析器
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    //静态资源处理
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //添加拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//    }
}
