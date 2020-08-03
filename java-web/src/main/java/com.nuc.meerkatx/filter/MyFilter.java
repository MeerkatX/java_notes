package com.nuc.meerkatx.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/31
 * @Description: 过滤器，过滤请求，设置，登录验证 Shrio
 */
@WebFilter(urlPatterns = "/servlet/hello3")//配置相关过滤路径
public class MyFilter implements Filter {

    //初始化
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //web服务启动就初始化了过滤器
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("Filter 执行前");
        chain.doFilter(request,response); //需要继续吧请求传到下一个filter
        System.out.println("Filter 执行后");
    }

    //销毁 web服务器关闭的时候
    @Override
    public void destroy() {

    }
}
