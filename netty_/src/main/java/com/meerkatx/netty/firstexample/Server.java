package com.meerkatx.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/25
 * @Description: netty模板代码
 * curl http://localhost:8989
 * curl -X POST http://localhost:8989
 */
public class Server {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();//事件循环组，获取连接转到worker处理 是一个线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();//worker循环组具体处理任务

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());//定义子处理器

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(8989).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) System.out.println("connect");
                    else System.out.println("faild");
                }
            });
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
