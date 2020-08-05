package com.nuc.meerkatx;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/30
 * @Description:
 */
@WebServlet(name = "Hello2Servlet", urlPatterns = "/hello2")
public class Hello2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.getServletConfig(); servlet配置
//        this.getServletContext(); servlet上下文
//        this.getInitParameter(); 获得初始化参数

        ServletContext servletContext = this.getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/hello");
        requestDispatcher.forward(req,resp);
        String name = (String) servletContext.getAttribute("name");
        PrintWriter writer = resp.getWriter();
        writer.print("hello servlet : " + name);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
