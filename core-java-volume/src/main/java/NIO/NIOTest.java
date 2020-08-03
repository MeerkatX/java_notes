package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/13
 * @Description: 非阻塞IO模型demo （并非java NIO）
 */
public class NIOTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8888));
        List<SocketChannel> socketChannels = new ArrayList<>();//用来保存所有连接到socket
        while(true){
            //如果一直没消息、没链接会造成CPU空转
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel !=null){
                /*
                @return  The socket channel for the new connection,
                or <tt>null</tt> if this channel is in non-blocking mode
                and no connection is available to be accepted
                 */
                //因为非阻塞，直接返回，所以这里进行判空
                socketChannel.configureBlocking(false);//连接后设置为非阻塞
                socketChannels.add(socketChannel);
            }
            for (SocketChannel s:socketChannels){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int count = s.read(byteBuffer);
                if (count > 0){
                    String receivedMassage = new String(byteBuffer.array(), 0, count);
                    System.out.println(receivedMassage);
                }
            }
        }
    }
}
