package com.meerkatx.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: 徐少伟
 * @Date: 2020/2/28
 * @Description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//责任链模式
        pipeline.addLast(new IdleStateHandler(5,7,10,TimeUnit.SECONDS));
        //在一定时间内没有读写，会触发 空闲检测处理器

        pipeline.addLast(new MyServerHandler());
    }
}
