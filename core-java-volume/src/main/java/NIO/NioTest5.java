package NIO;

import java.nio.ByteBuffer;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/2
 * @Description: ByteBuffer 类型化的put与get方法
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(15);
        byteBuffer.putLong(50000000L);
        byteBuffer.putDouble(14.33);
        byteBuffer.putChar('你');
        byteBuffer.putShort((short)2);
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());//如果不对应，取出来会报错
    }
}
