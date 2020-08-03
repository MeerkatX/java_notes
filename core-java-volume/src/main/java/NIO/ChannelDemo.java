package NIO;

import java.io.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/13
 * @Description:
 */
public class ChannelDemo {
    public static void main(String[] args) throws Exception {

        //用于文件的数据读写，只能是阻塞模式
        FileChannel fileInChannel = new FileInputStream("in.txt").getChannel();
        FileChannel fileOutChannel = new FileOutputStream("in.txt").getChannel();

        //文件随机访问类
        FileChannel fileChannel = new RandomAccessFile("in.txt", "rw").getChannel();


        // 套接字通道，用于Socket套接字TCP连接的数据读写
        SocketChannel socketChannel = SocketChannel.open();

        //服务器套接字通道 允许我们监听一个TCP链接请求 为每个监听到的请求创建一个SocketChannel套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        DatagramChannel datagramChannel = DatagramChannel.open();//UDP 用于数据报通道
    }
}
