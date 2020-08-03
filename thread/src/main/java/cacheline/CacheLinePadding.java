package cacheline;

import sun.misc.Contended;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/1
 * @Description: 缓存行，一个缓存行读8个字节 16bit
 * 每次读取内存都是一块一块读，这一块就是缓存行
 */
public class CacheLinePadding {


    private static class T {
        public volatile long x = 0;//内存可见性，写入到MESI cache缓存一致性协议
        // modified exclusive shared invalid 当volatile写的时候设置别的缓存行为 invalid标记(失效)
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 1000_0000L; i++) {
                arr[0].x = i;
            }
        });


        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 1000_0000L; i++) {
                arr[1].x = i;
            }
        });

        final long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
    }
}
