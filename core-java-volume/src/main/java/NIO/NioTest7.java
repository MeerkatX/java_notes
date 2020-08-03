package NIO;

import java.nio.ByteBuffer;

/**
 * @Auther: 徐少伟
 * @Date: 2020/3/3
 * @Description: 只读buffer
 * <p>
 * 只读buffer，我们可以随时将一个普通buffer调用asReadOnlyBuffer方法
 * 返回一个只读buffer，但是不能将一个只读buffer转换为读写buffer
 * 同slice一样都是同一份底层数组，修改buffer就会修改readonly
 */
public class NioTest7 {

    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass());
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        ByteBuffer readonlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readonlyBuffer.getClass());
        readonlyBuffer.position(0);

        byteBuffer.position(0);
        byteBuffer.put((byte) 10);

        while (readonlyBuffer.hasRemaining()) {
            System.out.println(readonlyBuffer.get());
        }
        //readonlyBuffer.put((byte) 2);
    }
}
