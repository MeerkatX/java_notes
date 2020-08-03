package com.meerkatx.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Auther: 徐少伟
 * @Date: 2020/2/27
 * @Description:
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息:" + msg+"\n");
            } else {
                ch.writeAndFlush("[自己]" + msg + "\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //连接建立
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器]-" + channel.remoteAddress() + "加入\n");

        channelGroup.add(channel);


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //连接断
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]" + channel.remoteAddress() + "离开\n");
        //channelGroup.remove(channel);//会自动将断开的channel移除，所以不需要这句话
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接处于活动状态
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //连接处于断开状态
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
