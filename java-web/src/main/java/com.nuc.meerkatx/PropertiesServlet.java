package com.nuc.meerkatx;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/30
 * @Description: 读取资源文件
 */
@WebServlet(urlPatterns = {"/hello3", "/servlet/hello3"})
public class PropertiesServlet extends HelloServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/config.yml");
        Properties properties = new Properties();
        properties.load(in);
        String name = properties.getProperty("name");
        String passwd = properties.getProperty("passwd");
        resp.getWriter().write(name + ": " + passwd + "中文 中文");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
