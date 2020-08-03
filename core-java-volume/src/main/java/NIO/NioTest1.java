package NIO;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @Auther: 徐少伟
 * @Date: 2020/2/29
 * @Description:
 */
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);//分配大小10的缓冲区
        for (int i = 0; i < buffer.capacity(); ++i){
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }
        buffer.flip();//翻转状态 将写改为读
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
