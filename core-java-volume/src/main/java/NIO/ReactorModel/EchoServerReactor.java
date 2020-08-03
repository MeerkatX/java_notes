package NIO.ReactorModel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/14
 * @Description:
 * 单线程Reactor模式 采用以下两种函数来完成
 * selectionKey.attach(new AcceptorHandler()); // 塞入处理类
 * selectionKey.attachment(); // 取出处理类
 *
 *
 * 单线程Reactor模式下，反应器Reactor和处理器Handler都执行在同一个线程上，当其中一个Handler阻塞时，
 * 会导致其他所有的Handler都无法执行。当被阻塞的Handler不仅仅负责输入和输出处理的业务，还包括负责监听连接的
 * AcceptorHandler，一旦被阻塞，会导致整个服务不能接收新的连接，使得服务器变得不可用。
 */
@Slf4j
public class EchoServerReactor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocket;

    public EchoServerReactor() throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(8888));
        serverSocket.configureBlocking(false);

        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new AcceptorHandler());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    //Reactor负责dispatch收到的事件
                    SelectionKey sk = it.next();
                    dispatch(sk);
                }
                selected.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //分发任务到不同handler
    void dispatch(SelectionKey sk) {
        Runnable handler = (Runnable) sk.attachment();
        //调用之前attach绑定到选择键的handler处理器对象
        if (handler != null) {
            handler.run();
        }
    }

    //新连接处理器（只处理 accept 事件）
    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocket.accept();
                if (socketChannel != null) new EchoHandler(selector, socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }

}
