package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/3
 * @Description: 关于buffer的Scattering与Gathering
 * <p>
 * Scattering将来自一个channel中的数据读到多个buffer
 * Gathering为写
 */
public class NioTest11 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//打开socket
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);
        int massageLength = 2 + 3 + 4;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            //可以使用telnet来连接发送
            int byteRead = 0;
            while (byteRead < massageLength) {
                long r = socketChannel.read(byteBuffers);
                byteRead += r;
                System.out.println("bytesRead" + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer -> "position" + buffer.position()
                        + ",limit:" + buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.flip();
            });
            long bytesWriten = 0;
            //回显，把信息重新写回到
            while (bytesWriten < massageLength) {
                long r = socketChannel.write(byteBuffers);
                bytesWriten += r;
            }
            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.clear();
            });
            System.out.println("byteRead" + byteRead + " byteWritten " + bytesWriten);
        }
    }
}
