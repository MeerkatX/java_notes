# Java核心技术

## IO 

(同步、异步、阻塞、非阻塞)

这里复制了2次，即物理设备到内核缓冲区，内核缓冲区到用户缓冲区

- 客户端请求：Linux通过网卡读取客户端的请求数据，将数据读取到内核缓冲区。
-  获取请求数据：Java服务器通过read系统调用，从Linux内核缓冲区读取数据，再送入Java进程缓冲区。
-  服务器端业务处理：Java服务器在自己的用户空间中处理客户端的请求。
-  服务器端返回数据：Java服务器完成处理后，构建好的响应数据，将这些数据从用户缓冲区写入内核缓冲区。这里用到的是write系统调用。
-  发送给客户端：Linux内核通过网络IO，将内核缓冲区中的数据写入网卡，网卡通过底层的通信协议，会将数据发送给目标客户端。

![IO模型](readme.assets/IO.png)

### 多路复用IO (epoll)

- 选择器注册。在这种模式中，首先，将需要read操作的目标socket网络连接，提前注册到select/epoll选择器中，Java中对应的选择器类是Selector类。然后，才可以开启整个IO多路复用模型的轮询流程。
- 就绪状态的轮询。通过选择器的查询方法，查询注册过的所有socket连接的就绪状态。通过查询的系统调用，内核会返回一个就绪的socket列表。当任何一个注册过的socket中的数据准备好了，内核缓冲区有数据（就绪）了，内核就将该socket加入到就绪的列表中。
  当用户进程调用了select查询方法，那么整个线程会被阻塞掉。
- 用户线程获得了就绪状态的列表后，根据其中的socket连接，发起read系统调用，用户线程阻塞。内核开始复制数据，将数据从内核缓冲区复制到用户缓冲区。
- 复制完成后，内核返回结果，用户线程才会解除阻塞的状态，用户线程读取到了数据，继续执行。

```bash
# linux指令
ps -ef|grep java # 查看所有java的pid
# 每个pid对应/proc下一个文件夹
cd /proc
cd /{pid}
# 文件描述符在fd文件夹下
cd fd
# 这样就能看到所有IO的对文件描述符的操作
```



![mio](readme.assets/mNIO.png)

见[nettystudy](../nettystudy)

Java NIO

redis -> epoll

nginx -> epoll

netty -> epoll

tomcat -> epoll

rocketMQ -> netty

kafka -> netty

### BIO 阻塞IO

![BIO](readme.assets/BIO.png)

[code](./src/main/java/BIO)

### NIO 非阻塞IO

![NIO](readme.assets/NIO.png)

[code](./src/main/java/NIO)

### AIO 异步非阻塞IO

- 当用户线程发起了read系统调用，立刻就可以开始去做其他的事，用户线程不阻塞。
- 内核就开始了IO的第一个阶段：准备数据。等到数据准备好了，内核就会将数据从内核缓冲区复制到用户缓冲区（用户空间的内存）。
- 内核会给用户线程发送一个信号（Signal），或者回调用户线程注册的回调接口，告诉用户线程read操作完成了。
- 用户线程读取用户缓冲区的数据，完成后续的业务操作

![AIO](readme.assets/AIO.png)

[code](./src/main/java/AIO)

### Java NIO 和 BIO

- BIO是面向流的，NIO是面向缓冲区的

OIO是面向字节流或字符流的，在一般的OIO操作中，我们以流式的方式顺序地从一个流（Stream）中读取一个或多个字节，因此，我们不能随意地改变读取指针的位置。而在NIO操作中则不同，NIO中引入了Channel（通道）和Buffer（缓冲区）的概念。读取和写入，只需要从通道中读取数据到缓冲区中，或将数据从缓冲区中写入到通道中。NIO不像OIO那样是顺序操作，可以随意地读取Buffer中任意位置的数据。

- BIO阻塞，NIO非阻塞
- BIO没有选择器，NIO有

#### 部分Linux命令

`nc localhost port` 连接

`strace -ff -o out /usr/xxx/java TestSocket` -ff抓取 -o 输出到out 追踪操作系统调用

`ulimit -SHn 1000000` 设置文件打开数量配置（仅在当前用户环境有效） 一般netty和ElasticSearch都应该配置

 其中 -S为==软性极限值== -H表示==硬性极限值== $2^{20}$为最大值 

终极解除Linux系统的最大文件打开数量的限制，可以通过编辑Linux的极限配置文件`/etc/security/limits.conf`来解决，修改此文件，加入如下内容：

    soft nofile 1000000 
    hard nofile 1000000
soft nofile表示软性极限，hard nofile表示硬性极限。

避免超过==1024==的连接导致 `Socket/File:Can’t open so many files` 

`netstat -natp` 查看网络端口连接（监听）状态

`/proc/pid/fd` linux万物皆文件，proc下一个进程一个文件

#### BIO NIO 多路复用器（select， poll， epoll）Linux底层

BIO阻塞

accept(2,... 处阻塞

read(5,... 处阻塞

NIO非阻塞 
底层调用`system.call`  `socket`  

`fcntl (O_Non-BLOCK)` 

accept()立即返回 -1 或 具体客户端

read()立即返回 -1 或 具体数据

多路复用

[Linux IO](https://segmentfault.com/a/1190000003063859)

select() jdk1.4前采用，单个进程能够监视的文件描述符数量linux默认为1024

轮询文件描述符。

epoll() 事件驱动不轮询 epoll使用一个文件描述符管理多个描述符，将用户关系的文件描述符的事件存放到内核的一个事件表中，这样在用户空间和内核空间的copy只需一次。

```java
socket.configureBlocking(false);
while(){
    client = socket.accpet()//不阻塞
    client.configureBlocking(false);
    list.add(client)
    for(Socket s: list){
        s.read()//不阻塞
    }
}
```
Java NIO 就是`select()`利用epoll多路复用器，对每一个事件,Key,自主的进行read

`epoll_create`

`epoll_wait`

`epoll_ctl`

### zero copy

[知乎](https://zhuanlan.zhihu.com/p/78869158)

传统数据流 如IO最开始的图，拷贝了4次

![cp](readme.assets/cp.png)

零拷贝减少了数据从内核空间到用户空间的复制，数据直接在内核空间中移动（ 通过调用Linux `sendfile`方法） 

![zerocp](readme.assets/zerocp.png)

### Reactor模式

[Doug Lea 的 Scalable IO of Java](http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf)

主要由两大角色组成：Reactor反应器线程、Handlers处理器

- Reactor线程的职责，负责响应IO事件，并且分发到Handlers处理器。

- Handlers处理器的职责，非阻塞执行业务处理逻辑

> Connection Per Thread

BIO 通常由于阻塞，采用一个线程处理一个链接的模式(Connection Per Thread) 早期tomcat 缺点在于：

对应大量链接，需要耗费大量线程资源，对线程资源要求太高。

线程创建、销毁、切换代价大。

> Reactor

Reactor反应器：负责查询IO事件，当检测到一个IO事件，将其发送给相应的Handler处理器去处理。这里的IO事件，就是NIO中选择器监控的通道IO事件。
Handler处理器：与IO事件（或者选择键）绑定，负责IO事件的处理。完成真正的连接建立、通道的读取、处理业务逻辑、负责将结果写出到通道等。

> 单线程Reactor

```java
 //单线程Reactor模式 采用以下两种函数来完成
 selectionKey.attach(new AcceptorHandler()); // 塞入处理类
 selectionKey.attachment(); // 取出处理类
```

单线程Reactor模式下，反应器Reactor和处理器Handler都执行在同一个线程上，当其中一个Handler阻塞时，
会导致其他所有的Handler都无法执行。当被阻塞的Handler不仅仅负责输入和输出处理的业务，还包括负责监听连接的
`AcceptorHandler`，一旦被阻塞，会导致整个服务不能接收新的连接，使得服务器变得不可用。

> 多线程Reactor

（1）将负责输入输出处理的`IOHandler`处理器的执行，放入独立的线程池中。这样，业务处理线程与负责服务监听和IO事件查询的反应器线程相隔离，避免服务器的连接监听受到阻塞。
（2）如果服务器为多核的CPU，可以将反应器线程拆分为多个子反应器`（SubReactor）`线程；同时，引入多个选择器，每一个`SubReactor`子线程负责一个选择器。这样，充分释放了系统资源的能力；也提高了反应器管理大量连接，提升选择大量通道的能力。

## 细节

### 栈（运算）

[你真的了解 i++, ++i 和 i+++++i 以及 i+++i++ 吗？](http://blog.itpub.net/31561266/viewspace-2222093/)

[关于自增变量的一道面试题 int i = 1;i=i++结果是多少?](https://www.fznjava.cn/300.html)

```java
i = 1;
i += i++; //这个比较容易记错
System.out.println(i);
```

[code](./src/main/java/MainTest.java)

### `HashMap`

[Java集合之HashMap详解](https://www.fznjava.cn/286.html)

## proxy 动态代理

[code](./src/main/java/proxy)

## reference 四种引用：

[code](./src/main/java/reference)

- 强引用 Strong Reference `A a = new A();`
- 软引用 Soft Reference `SoftReference<Object> m = new SoftReference<>(new Object());`

    在使用软引用时，如果内存的空间足够，软引用就能继续被使用，而不会被垃圾回收器回收；只有在内存空间不足时，软引用才会被垃圾回收器回收

- 弱引用 Weak Reference `WeakReference<Object> o = new WeakReference<>(new Object());`
  
    JVM 进行垃圾回收，一旦发现弱引用对象，无论当前内存空间是否充足，都会将弱引用回收。
    目前已知用在了TreadLocal map中
    
- 虚引用 Phantom Reference

## serializable 序列化

## redis在[jedis](./src/main/java/jedis)下

# Java Web

