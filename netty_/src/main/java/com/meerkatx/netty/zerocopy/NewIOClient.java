package com.meerkatx.netty.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/6
 * @Description:
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        //This method is potentially much more efficient than a simple loop
        //that reads from this channel and writes to the target channel.  Many
        //operating systems can transfer bytes directly from the filesystem cache
        //to the target channel without actually copying them.
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);//transferTo使用了0拷贝

        System.out.println("发送总字节数:" + transferCount + "，时间:" + (System.currentTimeMillis() - startTime));

    }
}
