package ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/23
 * @Description:
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 60L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2));
//        threadPoolExecutor.prestartAllCoreThreads();
//        预先开启核心线程，可能由于BlockQueue取任务与往线程池中添加任务速度不匹配导致任务拒绝
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    for (;;) {
                    }
                }
            });
        }

    }
}
