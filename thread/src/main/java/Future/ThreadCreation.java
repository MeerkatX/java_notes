package Future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: 徐少伟
 * @Date: 2019/11/27
 * @Description:
 */
public class ThreadCreation {

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(1);

        /*
        开线程只有 new Thread()一种方式，可以通过继承Thread，
        实现Runnable或Callable，在run()、call()方法中写需要线程执行的代码
         */
        Thread thread1 = new Thread(new RunnableDemo());
        //Callable 可以有返回值，返回值通过 FutureTask 进行封装
        //FutureTask桥接 Callable 和 Runnable 因为 Thread只能接收Runnable接口的对象
        //FutureTask实现了Runnable(RunnableFuture的父接口)
        // public Thread(Runnable target)
        FutureTask<Integer> future = new FutureTask<>(new CallableDemo());
        Thread thread2 = new Thread(future);
        thread1.start();
        thread2.start();
        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        /*

        //Thread异常
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.out.println("遇到异常 : " + throwable.getMessage());
        });

        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("线程异常");
            //System.out.println(Thread.currentThread().getName());
        });
        thread1.start();
        thread1.join();
        //线程池 也是new Thread()
        //ThreadPoolExecutor

        */

    }
}
