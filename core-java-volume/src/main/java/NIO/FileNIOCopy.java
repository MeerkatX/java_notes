package NIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/13
 * @Description: 文件复制 NIO 版
 */
public class FileNIOCopy {

    public static void main(String[] args) throws Exception {
        File srcFile = new File("source.txt");
        File tarFile = new File("target.txt");

        System.out.println(srcFile.getAbsolutePath());
        if (!tarFile.exists()) tarFile.createNewFile();
        System.out.println(tarFile.getAbsolutePath());

        FileChannel source = new RandomAccessFile(srcFile, "r").getChannel();
        FileChannel target = new RandomAccessFile(tarFile, "rw").getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (source.read(byteBuffer) != -1) {
            byteBuffer.flip();//从写改为读
            //byteBuffer.rewind();//倒带可以重新读 (这个放这里没啥用，就是记一下api)
            int outlen = 0;
            while ((outlen = target.write(byteBuffer)) != 0) {
                System.out.println("写入" + outlen);
            }
            byteBuffer.clear();//从读改为写（重置）
            target.force(true);//强制刷新到硬盘
        }
        //关闭通道
        source.close();
        target.close();
    }
}
