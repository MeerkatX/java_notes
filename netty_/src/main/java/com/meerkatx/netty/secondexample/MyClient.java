package com.meerkatx.netty.secondexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/30
 * @Description: 简单的显示服务器消息，回传时间的netty程序
 */
public class MyClient {
    public static void main(String[] args) {
        //客户端一般只需要一个线程组，毕竟没有那么高的并发
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();//对于客户端是Bootstrap 服务端是 BootstrapServer

        //bootstrap主要起一个配置作用，把所有东西组合在一起
        bootstrap.group(worker).channel(NioSocketChannel.class).handler(new MyClientInitializer());
        try {
            //connect操作是所有操作的开始，也是看源码的开始。
            //netty所有操作都是异步的，所以需要Future去接结果,可以添加Listener，等结果完成后调用
            //sync等待结果完成，阻塞当前进程，响应中断，所以需要catch中断异常
            ChannelFuture channelFuture = bootstrap.
                    connect("localhost",8888).sync();

            //当初始化connect等完成后，sync阻塞返回执行下一步
            channelFuture.channel().closeFuture().sync();//这一步同样阻塞主线程，等待关闭所有通道

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //当所有任务结束，关闭“线程池”
            worker.shutdownGracefully();
        }
    }
}
