package com.meerkatx.netty.secondexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/30
 * @Description: socket编程
 */
public class MyServer {
    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();//bossGroup主要接受accept请求，建立连接后转发给worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();//主要业务处理，数据接受/发送 readable/writeable

        ServerBootstrap serverBootstrap = new ServerBootstrap();//服务器的总配置类，启动类

        serverBootstrap.group(bossGroup, workerGroup);//配置线程组
        serverBootstrap.channel(NioServerSocketChannel.class);//配置要用的Channel，服务器一般是ServerSocket
        // Netty有OIO NIO

        //serverBootstrap.channel();//配置ServerSocketChannel管道
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            //这里给 socketchannel 配置管道 childHandler这时候已经建立连接返回SocketChannel了
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //在初始化channel时就已经初始化了pipline，建立连接时调用initChannel往管道加入内容
                //加入之后同时去掉ChannelInitializer
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new MyServerHandler());
            }
        });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8888).sync();
            //Netty通常都是异步操作，需要Future接受结果，sync阻塞，直到bind完成
            //bind是一切开始，建议从bind开始阅读源码
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //任务结束关闭所有线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
