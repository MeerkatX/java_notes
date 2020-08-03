package NIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.*;
import java.nio.channels.FileChannel;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/13
 * @Description:
 */
public class BufferDemo {
    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//常用

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        DoubleBuffer doubleBuffer = DoubleBuffer.allocate(1024);
        FloatBuffer floatBuffer = FloatBuffer.allocate(1024);
        IntBuffer intBuffer = IntBuffer.allocate(1024);
        LongBuffer longBuffer = LongBuffer.allocate(1024);
        ShortBuffer shortBuffer = ShortBuffer.allocate(1024);

        //用于内存映射的ByteBuffer 如果对buffer修改就会直接对文件修改，而不用写到channel中 详情见NioTest9
        MappedByteBuffer mappedByteBuffer = new RandomAccessFile(new File("in.txt"), "r")
                .getChannel().map(FileChannel.MapMode.READ_ONLY, 0, 1);

    }
}
