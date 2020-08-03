package ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/7
 * @Description:
 */
public class ThreadPoolLearnToUse {

    private volatile int a = 0;

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();//AQS



        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //Executors 是创建 ThreadPoolExecutor 的工厂
        // 接口 Executor -> 接口继承 ExecutorService -> 抽象类实现 AbstractExecutorService -> 实际线程池 ThreadPoolExecutor
        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(参数);
        //实际创建线程池的方法
        /*
                return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
         */
        for (int i = 0; i < 1000; i++) {
            final int t = i;
            executorService.execute(() -> {
                System.out.println(t);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }




    }
}
