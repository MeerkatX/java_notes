package ThreadPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: CountDownLatchDemo
 * @Auther: MeerkatX
 * @Date: 2020-08-04 17:04
 * @Description:
 */
public class CountDownLatchDemo {

    private AtomicInteger i = new AtomicInteger(0);

    public int add() {
        return i.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        //可以替代 CountDownLatch，也可以替代 CyclicBarrier
        Phaser phaser = new Phaser(10);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 100000; j++) {
                    countDownLatchDemo.add();
                }
//                countDownLatch.countDown();
                phaser.arrive();
//                phaser.arriveAndAwaitAdvance();//类似CyclicBarrier的await方法
            });
        }
//        countDownLatch.await();
        phaser.awaitAdvance(phaser.getPhase());//表示等待当前phase当前同步点完成
        System.out.println(countDownLatchDemo.i);
        executor.shutdown();
    }
}
