package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 */
public class MyPhantomReference {

    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        PhantomReference<Object> phantomReference = new PhantomReference<>(new Object(), QUEUE);
        //虚引用 管理堆外内存（NIO相关），不可以被get
        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends Object> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println(poll);
                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
