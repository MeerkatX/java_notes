package NIO;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/13
 * @Description:
 */
public class FileNIOFastCopy {

    public static void main(String[] args) throws Exception {
        File srcFile = new File("source.txt");
        File tarFile = new File("target.txt");

        System.out.println(srcFile.getAbsolutePath());
        if (!tarFile.exists()) tarFile.createNewFile();
        System.out.println(tarFile.getAbsolutePath());


        FileChannel source = new RandomAccessFile(srcFile, "r").getChannel();
        FileChannel target = new RandomAccessFile(tarFile, "rw").getChannel();

        long size = source.size();
        long pos = 0;
        long count = 0;

        while (pos < size) {
            count = Math.min(size - pos, 1024);//每次复制1024个字节
            /*
            Transfers bytes into this channel's file from the given readable byte channel.

            This method is potentially much more efficient than a simple loop that reads from the source channel
            and writes to this channel. Many operating systems can transfer bytes directly from the source channel
            into the filesystem cache without actually copying them.
            [ 即 0 拷贝 ]
            transferTo | transferFrom | FileChannel.map
            不用复制了，速度更快，直接将source channel复制到文件系统缓存
             */
            pos += target.transferFrom(source, pos, count); //transferFrom直接复制
        }
        target.force(true);
        target.close();
        source.close();
    }
}
