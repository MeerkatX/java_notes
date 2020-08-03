package com.meerkatx.netty.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/6
 * @Description:
 */
public class OldClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);
        String fileName = "";
        InputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readcount = 0;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readcount = inputStream.read(buffer)) >= 0) {
            total += readcount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送字节数:" + total + " 耗时:" + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
