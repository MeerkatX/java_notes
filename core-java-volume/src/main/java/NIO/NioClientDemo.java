package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/24
 * @Description: 常规 Java NIO 模型：selector channel buffer
 * 完整版请看NioClient
 */
public class NioClientDemo {


    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();//打开一个Channel 工厂方法
        socketChannel.configureBlocking(false);//设置为非阻塞

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        socketChannel.connect(new InetSocketAddress(8899)); //连接到相应端口

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            //遍历每个key
            for (SelectionKey key : keys) {


                if (key.isConnectable()) {//连接标志
                    SocketChannel client = (SocketChannel) key.channel(); //获取连接上的channel

                    if (client.isConnectionPending()) {
                        /*
                        可以通过调用isConnectionPending方法来确定是否正在进行连接操作。
                         */

                        try {
                            //完成连接
                            client.finishConnect();

                            //写信息到服务端 nio byte buffer操作
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuffer.flip();

                            //输出 写流
                            client.write(writeBuffer);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    client.register(selector, SelectionKey.OP_READ);//连接之后将读标志位绑定

                }
                //读标志位
                else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int count = client.read(byteBuffer);
                    if (count > 0) {
                        String receivedMassage = new String(byteBuffer.array(), 0, count);
                        System.out.println(receivedMassage);
                    }
                }
                //处理完成后清空
                keys.clear();
            }
        }

    }
}
