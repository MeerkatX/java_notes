package com.meerkatx.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/30
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    //当有信息可读 就被netty调用，所以这里写一些具体业务代码
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //ChannelHandlerContext上下文类，保存了管道，通道引用
        System.out.println(ctx.channel().remoteAddress());//可以通过上下文获得通道，然后再利用通道获得地址
        System.out.println("client output:" + msg);//打印服务器传来的信息 msg
        ctx.writeAndFlush("From client:"+ LocalDateTime.now());//传回服务器相关信息
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //捕获异常，如果有异常就在这里处理
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("client channel active");
    }
}
