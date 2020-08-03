import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.Test;
import sun.awt.EmbeddedFrame;

import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/8/3
 * @Description:
 */
public class Byte2IntegerDecoderTester {
    @Test
    public void testByte2Integer() throws InterruptedException {
        ChannelInitializer ci = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new ByteToMessageDecoder() {
                    @Override
                    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                        while(in.readableBytes()>=4){
                            int i = in.readInt();
                            out.add(i);
                        }
                    }
                });
                ch.pipeline().addLast(new SimpleChannelInboundHandler(){

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                        Integer integer = (Integer) msg;
                        System.out.println(integer);
                    }
                });
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(ci);
        for(int i=0;i<100;i++){
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(i);
            channel.writeInbound(byteBuf);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

}
