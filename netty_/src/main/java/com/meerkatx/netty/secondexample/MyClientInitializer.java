package com.meerkatx.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/30
 * @Description: 客户端 管道 设置
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    //抽象类的模板方法。在这里设置管道信息一般
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//每个pipeline是在Channel新建的时候就创建好的
        pipeline.addLast(new LengthFieldBasedFrameDecoder(
                Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));//出站
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));//入站
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));//出站
        pipeline.addLast(new MyClientHandler());//入站
    }
}
