package BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/23
 * @Description:
 */
public class Client {


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        //读取服务器端数据
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //向服务器端发送数据
        PrintStream out = new PrintStream(socket.getOutputStream());

        while (true) {
            System.out.print("请输入: ");
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.println(str);
            out.flush();

            //String ret = input.readLine();//这里会阻塞等服务器发消息
            //System.out.println("服务器端返回过来的是: " + ret);
            // 如接收到 "OK" 则断开连接
//            if ("OK".equals(ret)) {
//                System.out.println("客户端将关闭连接");
//                break;
//            }
        }
//        out.close();
//        input.close();
//        socket.close();
    }
}
