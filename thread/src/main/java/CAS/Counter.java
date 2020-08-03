package CAS;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description: 循环 CAS 实现原子操作
 */
public class Counter {

    private AtomicInteger aint = new AtomicInteger(0);
    private LongAdder longAdder = new LongAdder();
    private int i = 0;

    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int k = 0; k < 100; k++) {
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.count();
                    counter.safeCount();
                }
            });
        }

        // 获取所有线程信息
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(false,false);
        //打印所有线程信息
        Arrays.stream(threadInfos).forEach(o->{
            System.out.println(o.getThreadId()+" "+o.getThreadName());
        });

        executor.shutdown();

        boolean loop = true;
        do {
            loop = !executor.awaitTermination(2, TimeUnit.SECONDS);
        } while (loop);
        System.out.println(counter.i + "  " + counter.aint.get());
    }

    public void count() {
        i++;
    }

    public void safeCount() {
        for (; ; ) {
            //死循环 自旋 cas
            int c = aint.get();
            if (aint.compareAndSet(c, ++c)) break;
        }
    }

}
