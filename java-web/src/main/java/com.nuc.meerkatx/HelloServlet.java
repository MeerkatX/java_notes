package com.nuc.meerkatx;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//tomcat7.0 + servlet 3.0支持注解的方式，可以不配置web.xml
@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.getServletConfig(); servlet配置
//        this.getServletContext(); servlet上下文
//        this.getInitParameter(); 获得初始化参数
        ServletContext servletContext = this.getServletContext();
        servletContext.setAttribute("name", "meerkatx");
        String value = servletContext.getInitParameter("value");
        PrintWriter writer = resp.getWriter();
        writer.print("hello servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}