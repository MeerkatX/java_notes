package com.nuc.meerkatx.spmvc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @ClassName: MyWebApplicationInitializer
 * @Auther: MeerkatX
 * @Date: 2020-08-05 21:25
 * @Description: mvc配置类，相当于web.xml Servlet3.0 特性
 * 具体在于
 * \@HandlesTypes(WebApplicationInitializer.class)
 * public class SpringServletContainerInitializer implements ServletContainerInitializer
 * 通过 SPI，查找spring-web下META-INF的services中javax.servlet.ServletContainerInitializer文件
 * 文件内容为：org.springframework.web.SpringServletContainerInitializer 所以Tomcat等容器在初始化servlet容器的时候
 * 会调用该类，而该类又会查找实现了WebApplicationInitializer的类
 *
 * spring 3.2 通过实现
 * AbstractAnnotationConfigDispatcherServletInitializer来配置 mvc
 */
@Slf4j
public class MyWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext){
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        log.info("onStartUp");
        webApplicationContext.register(MVCConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
        servlet.addMapping("/");//添加上下文路径地址
        servlet.setLoadOnStartup(1);//最优先启动
        servlet.setAsyncSupported(true); //设置允许异步线程
    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{RootConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[]{MVCConfig.class};
//    }
}
