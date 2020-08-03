package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/13
 * @Description:
 */
public class UDPDatagramChannelDemo {

    public static void main(String[] args) throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            byteBuffer.put(next.getBytes());
            byteBuffer.flip();
            datagramChannel.send(byteBuffer,new InetSocketAddress("localhost",8888));
            byteBuffer.clear();
        }
        datagramChannel.close();
    }



}
