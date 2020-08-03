package BIO;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/23
 * @Description:
 */
public class BIO {

    public static void main(String[] args) throws IOException {
        /*
        byte[] read = new byte[1024];
        InputStream in = new FileInputStream("D:\\ALLprojects\\Java_projects\\java_notes\\core-java-volume\\src\\main\\java\\BIO\\in.txt");
        while (in.read(read)!=-1){
            String str = new String(read, StandardCharsets.UTF_8);
            System.out.println(str);
        }
        */
        ThreadPoolExecutor tp = new ThreadPoolExecutor(2, 5, 60L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2));
        ServerSocket serverSocket = new ServerSocket(8888);
        serverSocket.setReuseAddress(true);
//        serverSocket.bind(new InetSocketAddress(8988));
        while (true) {
            final Socket socket = serverSocket.accept();
            tp.execute(new Runnable() {
                           @Override
                           public void run() {
                               try {
                                   // 读取客户端数据
                                   BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                   String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                                   // 处理客户端数据
                                   System.out.println("客户端发过来的内容:" + clientInputStr);

                                   // 向客户端回复信息
                                   PrintStream out = new PrintStream(socket.getOutputStream());
                                   System.out.print("请输入:\t");
                                   // 发送键盘输入的一行
                                   String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                                   out.println(s);

                                   out.close();
                                   input.close();
                               } catch (Exception e) {
                                   System.out.println("服务器 run 异常: " + e.getMessage());
                               } finally {
                                   if (socket != null) {
                                       try {
                                           socket.close();
                                       } catch (Exception e) {
                                           System.out.println("服务端 finally 异常:" + e.getMessage());
                                       }
                                   }
                               }
                           }
                       }
            );
        }
    }


}
