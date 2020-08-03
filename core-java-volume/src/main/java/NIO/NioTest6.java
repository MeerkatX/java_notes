package NIO;

import java.nio.ByteBuffer;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/2
 * @Description: 分片Slice Buffer与原有Buffer共享相同的底层数组
 */
public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(6);

        ByteBuffer sliceBuffer = byteBuffer.slice(); //如何获取切片

        //切片后得到的是同一片内存区域，修改sliceBuffer就修改byteBuffer对应的值
        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }
        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }


    }
}
