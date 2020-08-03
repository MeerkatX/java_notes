package NIO;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/3
 * @Description: 内存映射文件
 */
public class NioTest9 {
    public static void main(String[] args)throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");//随机读写文件
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte)'a');//修改内存就直接修改文件
        randomAccessFile.close();
    }

}
