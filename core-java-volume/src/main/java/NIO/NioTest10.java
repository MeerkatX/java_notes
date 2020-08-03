package NIO;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/3
 * @Description:文件锁
 */
public class NioTest10 {
    public static void main(String[] args)throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.lock(3,6,true);
        System.out.println(fileLock.isValid());
        System.out.println(fileLock.isShared());
        fileLock.release();
        randomAccessFile.close();
    }
}
