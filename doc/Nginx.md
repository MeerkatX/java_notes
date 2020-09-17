# Nginx

反向代理、负载均衡、动静分离、高可用集群

## 反向代理

### 正向代理

通过代理服务器访问目标网站(需要客户端配置)

（梯子）

### 反向代理

客户端对代理无感知，客户端不需要配置就可以访问，只需要将请求发送到反向代理服务器，由反向代理服务器选择目标服务器获取数据后，在返回给客户端。

反向代理服务器和目标服务器对外就是一个服务器，暴露的是代理服务器的地址，隐藏了真实服务器IP地址。

User -> [ 反向代理服务器（80） -> 目标服务器1（192.168.0.1:8080）或 目标服务器2 （192.168.0.2:8080）]

![NGINX,Python,WEB服务](Nginx.assets/-1451750843.png)

## 负载均衡

将请求分发到多个服务器上

## 动静分离

加快网站解析速度，把动态页面和静态页面由不同的服务器来解析，加快解析速度，降低原来服务器压力

## 命令

```bash
# Ubuntu下用sudo apt-get install nginx安装
```

路径可能不同，但是命令相同

常用命令：

```bash
meerkatx@ubuntu:/usr/sbin$ ./nginx -V # 查看nginx版本

meerkatx@ubuntu:/etc/nginx$ sudo nginx -s quit #关闭nginx服务 sudo nginx -s stop

meerkatx@ubuntu:/usr/sbin$ sudo ./nginx # 开启nginx
# nginx配置文件 nginx.conf
meerkatx@ubuntu:/etc/nginx$ ls
conf.d          koi-win            nginx.conf       sites-enabled
fastcgi.conf    mime.types         proxy_params     snippets
fastcgi_params  modules-available  scgi_params      uwsgi_params
koi-utf         modules-enabled    sites-available  win-utf
# 重新加载配置文件
meerkatx@ubuntu:/usr/sbin$ sudo ./nginx -s reload

```

## 配置文件

```

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```

### 全局块

设置影响nginx服务器整体运行的配置指令，主要包括配置运行nginx服务器的用户组、允许生成的worker process数，进程PID存放路径、日志存放路径和类型以及配置文件的引入等。

```
ser www-data;
worker_processes auto;
pid /run/nginx.pid;
include /etc/nginx/modules-enabled/*.conf;

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;
```

### Events块

主要影响Nginx服务器和用户的网络连接：

- 是否开启对多worker process下的网络连接进行序列化
- 是否允许同时接受多个网络连接
- 选取哪种事件驱动模型来处理连接请求
- 每个work process可以同时支持的最大连接数等

```
events {
        worker_connections 768;# 支持最大连接数
        # multi_accept on;
}
```

### Http块

Nginx服务器配置中最频繁的部分。

Http全局块：

```
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;
```

Server块：

```
    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        # 正则表达式
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }
```

