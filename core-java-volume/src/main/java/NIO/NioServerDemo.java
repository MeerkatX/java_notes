package NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/24
 * @Description: 常规 Java NIO 模型：selector channel buffer
 * 完整版请看NioServer
 *
 *  IO事件不是对通道的IO操作，而是通道的某个IO操作的一种就绪状态，表示通道具备完成某个IO操作的条件。
 */
public class NioServerDemo {

    public static void main(String[] args) throws Exception {

        //服务端常规操作
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//通过工厂方法得到一个channel
        serverSocketChannel.configureBlocking(false);//设置为非阻塞
        serverSocketChannel.bind(new InetSocketAddress(8899));//绑定监听端口 类似于BIO

        //Selector常规操作
        Selector selector = Selector.open();//通过工厂方法得到Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//将Channel注册到Selector

        int k = SelectionKey.OP_ACCEPT | SelectionKey.OP_READ; //当监听多种事件，可以用或运算

        while (true) {
            //自旋监听事件(轮询)
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            //针对每一个key进行对应操作
            keys.forEach(key -> {

            });

            keys.clear();//处理完清空，使得可以重新加入事件Keys

        }

    }

}
