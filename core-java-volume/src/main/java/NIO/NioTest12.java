package NIO;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/4
 * @Description: selector
 */
public class NioTest12 {
    public static void main(String[] args) throws Exception {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();//获取selector对象，常见构造方式

        for (int i = 0; i < ports.length; ++i) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//创建channel
            serverSocketChannel.configureBlocking(false);//调整阻塞模式 ，false不阻塞
            //用selector的话。这样read或者accept时就不会阻塞导致下面代码不能运行
            // (true) 会导致java.nio.channels.IllegalBlockingModeException
            ServerSocket serverSocket = serverSocketChannel.socket();//通过socket方法获取与通道关联的socket
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);//进行绑定

            //注册
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//共4种操作，当前必选接受连接accept
            System.out.println("监听端口" + ports[i]);
        }

        while (true) {
            int numbers = selector.select();//返回整型，key的数量
            System.out.println("numbers:" + numbers);
            Set<SelectionKey> keySet = selector.selectedKeys();//获取select后得到的keys集合 （包含channel,select信息）
            Iterator<SelectionKey> iter = keySet.iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isAcceptable()) {
                    //如果是前边绑定的accept事件，运行以下代码
                    //获取selectionKey下包含的channel信息
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);//绑定到selector，监听read事件
                    iter.remove();//将key移除，因为已经完成了accept事件
                    System.out.println("获取客户端连接" + socketChannel);
                } else if (selectionKey.isReadable()) {
                    //如果发生read事件
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();//获取通道，读取数据
                    int bufferRead = 0;
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);//将数据读到buffer中
                        if (read <= 0)
                            break;
                        byteBuffer.flip();//翻转，改为写buffer
                        socketChannel.write(byteBuffer);//将buffer写入到channel中，此时客户端应当收到回显的数据
                        bufferRead += read;
                    }
                    System.out.println("读取" + bufferRead + ",来自于" + socketChannel);
                    iter.remove();//事件处理完后一定要去掉
                }
            }
        }

    }

}

