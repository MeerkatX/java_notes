package com.meerkatx.netty.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/6
 * @Description:
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //serverSocketChannel.bind(new InetSocketAddress(8899));
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);//当前一个连接关闭，连接处于time wait状态
        // 会导致绑定的端口号在之后不能绑定，当setReuseAddress的话可以

        serverSocket.bind(new InetSocketAddress(8899));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel socketChannel1 = serverSocketChannel.accept();
            socketChannel1.configureBlocking(false);
            int count = 0;
            while (-1 != count) {
                try {
                    count = socketChannel1.read(byteBuffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            byteBuffer.rewind();//倒带 position
        }

    }
}
