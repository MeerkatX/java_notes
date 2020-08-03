package com.meerkatx.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Auther: 徐少伟
 * @Date: 2020/2/28
 * @Description:
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//责任链模式
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));//聚合
        //负责处理握手，控制帧，心跳检测等
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // "/ws" url     通常ws://localhost:8899/ws

        pipeline.addLast(new TextWebSocketHandler());
    }
}
