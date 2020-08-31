package com.nuc.meerkatx.springbootstudy.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/31
 * @Description: 拦截器，编写完成后再FilterConfig来配置，加入进去
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    //return true 放行，执行下一个拦截器，false 不执行拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("==============处理前================");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("==============处理后================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("===============清理============");
    }
}
