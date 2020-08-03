# Java Web

## Demo

maven创建`webapp`项目，导入servlet相关依赖

```xml
        <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
        </dependency>
```

编写逻辑代码，继承`HttpServlet`

```java
//tomcat7.0 + servlet 3.0支持注解的方式，可以不配置web.xml
@WebServlet("/hello")
```

```java
public class HelloServlet extends HttpServlet {
	
    //doGet方法用来接受get请求，这里响应也是HttpServlet模板方法
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.print("hello servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

其中Servlet接口->GenericServlet抽象类->HttpServlet抽象类

![image-20200730163215790](readme.assets/image-20200730163215790.png)

```xml
<!--之后配置web.xml，将servlet注册进来（Tomcat容器可以读到相应的类）-->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0"
         metadata-complete="true">
    <!--注册servlet-->
    <servlet>
        <servlet-name>hello</servlet-name>
        <servlet-class>com.nuc.meerkatx.HelloServlet</servlet-class>
    </servlet>

    <!--servlet请求路径-->
    <servlet-mapping>
        <servlet-name>hello</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

</web-app>
```

最后将应用放置到tomcat的webapp下，将项目打包成war包

或者配置idea

![image-20200730163958078](readme.assets/image-20200730163958078.png)

![image-20200730164029149](readme.assets/image-20200730164029149.png)

配置好之后直接run就可以了

## Servlet原理

servlet是由web服务器调用，web服务器在收到浏览器请求之后

tomcat即一种**web容器**，类似于spring是bean的容器一样，tomcat连接后通过xml配置文件等实例化servlet

web容器接受到浏览器http请求后，会产生请求和响应

> 请求 request

请求头，请求体

> 响应 response

响应头，响应体

之后调用 Servlet 接口中的 service 

### Service

```java
//Servlet接口中的
public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;
```

web容器根据xml生成我们自己写的实际逻辑类，通过接口回调调用响应的逻辑代码

HttpServlet.java下的实现的service方法：

```java

    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException
    {
        HttpServletRequest  request;
        HttpServletResponse response;
        
        if (!(req instanceof HttpServletRequest &&
                res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;

        service(request, response);//转发到实际service
    }
```

同样是HttpServlet.java具体service方法

```java
protected void service(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
{
    String method = req.getMethod();//获取请求方法，get post put delete

    if (method.equals(METHOD_GET)) {
        long lastModified = getLastModified(req);
        if (lastModified == -1) {
            // servlet doesn't support if-modified-since, no reason
            // to go through further expensive logic
            
            doGet(req, resp);//这一块回调子类方法，即自己编写的逻辑代码。 属于模板设计模式
            
        } else {
            long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
            if (ifModifiedSince < lastModified) {
                // If the servlet mod time is later, call doGet()
                // Round down to the nearest second for a proper compare
                // A ifModifiedSince of -1 will always be less
                maybeSetLastModified(resp, lastModified);
                doGet(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        }

    } else if (method.equals(METHOD_HEAD)) {
        long lastModified = getLastModified(req);
        maybeSetLastModified(resp, lastModified);
        doHead(req, resp);

    } else if (method.equals(METHOD_POST)) {
        doPost(req, resp);
        
    } else if (method.equals(METHOD_PUT)) {
        doPut(req, resp);
        
    } else if (method.equals(METHOD_DELETE)) {
        doDelete(req, resp);
        
    } else if (method.equals(METHOD_OPTIONS)) {
        doOptions(req,resp);
        
    } else if (method.equals(METHOD_TRACE)) {
        doTrace(req,resp);
        
    } else {
        //
        // Note that this means NO servlet supports whatever
        // method was requested, anywhere on this server.
        //

        String errMsg = lStrings.getString("http.method_not_implemented");
        Object[] errArgs = new Object[1];
        errArgs[0] = method;
        errMsg = MessageFormat.format(errMsg, errArgs);
        
        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
    }
}
```

Request从service拿到请求，并把请求之后的响应丢给response

### ServletContext

web容器在启动的时候，它会为每个web程序都创建一个对应的ServletContext对象，代表当前web应用。context管理

- 共享数据

```java
ServletContext servletContext = this.getServletContext();
servletContext.setAttribute("name", "meerkatx");
```

```java
ServletContext servletContext = this.getServletContext();
String name = (String) servletContext.getAttribute("name");
```

- 来获取初始化参数

```xml
<!--web.xml-->
<context-param>
    <param-name>name</param-name>
    <param-value>hello</param-value>
</context-param>
```

```java
ServletContext servletContext = this.getServletContext();
String value = servletContext.getInitParameter("value");
```

- 请求转发（转发路径是不会变的，重定向才会变路径）

```java
ServletContext servletContext = this.getServletContext();
RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/hello");
requestDispatcher.forward(req,resp);
```

- 浏览资源文件

在resource下新建配置文件 config.yml

```java
InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/config.yml");
Properties properties = new Properties();
properties.load(in);
String name = properties.getProperty("name");
String passwd = properties.getProperty("passwd");
resp.getWriter().print(name + ": " + passwd);
```

### HttpServletRequest



### HttpServletResponse



## Filter

### 过滤器用法

注解方式配置web应用，这样就不用在web.xml中编写了。、

实现Filter接口，必须实现init ,  doFilter,  destory三个方法 通常逻辑代码在doFilter中编写

```java
@WebFilter(urlPatterns = "/servlet/hello3")//配置相关过滤路径 “ /* ” 即所有
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
```

