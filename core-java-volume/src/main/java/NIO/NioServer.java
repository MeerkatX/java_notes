package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/5
 * @Description:
 */
public class NioServer {
    private static Map<String, SocketChannel> channelMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();//阻塞，等事件发生时会返回(事件发生数)
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            selectionKeySet.forEach(selectionKey -> {
                final SocketChannel client;
                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();//返回父类SelectableChannel
                        client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        String key = "[" + UUID.randomUUID().toString() + "]";//随机一个key用来唯一指定channel
                        channelMap.put(key, client);//保存到map
                    } else if (selectionKey.isReadable()) {
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if (count > 0) {
                            readBuffer.flip();
                            Charset charset = Charset.forName("UTF-8");
                            String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                            System.out.println(client + ":" + receivedMessage);
                            String sendKey = null;
                            for (Map.Entry<String, SocketChannel> entry : channelMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }

                            for (Map.Entry<String, SocketChannel> entry : channelMap.entrySet()) {
                                SocketChannel value = entry.getValue();
                                ByteBuffer writebuffer = ByteBuffer.allocate(1024);
                                writebuffer.put((sendKey + ":" + receivedMessage).getBytes());
                                writebuffer.flip();
                                value.write(writebuffer);
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            selectionKeySet.clear();

        }

    }
}
