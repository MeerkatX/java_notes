package Future;

import javafx.concurrent.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/15
 * @Description: Fail Fast 快速失败，即如果有一个任务失败了，就停止其他线程的任务
 */
public class ListenableFutureDemo {
    static List<MyTask> tasks = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        MyTask t1 = new MyTask("t1", 10, 1);
        MyTask t2 = new MyTask("t2", 5, 1);
        MyTask t3 = new MyTask("t3", 3, 0);
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        CompletableFuture f1 = CompletableFuture.supplyAsync(() -> t1.call()).thenAccept((result) -> callback(result, t1));
        CompletableFuture f2 = CompletableFuture.supplyAsync(() -> t2.call()).thenAccept((result) -> callback(result, t2));
        CompletableFuture f3 = CompletableFuture.supplyAsync(() -> t3.call()).thenAccept((result) -> callback(result, t3));
        System.in.read();
    }

    public static void callback(int result, MyTask task) {
        //if (!result) System.exit(0);
        //这里要考虑同步，如果有两个任务失败，就又cancel了一次，除非cancel无状态
        if (result == 0) {
            for (MyTask t : tasks)
                if (task != t) {
                    t.cancel();
                }
            //处理结束流程
            //通知其他线程结束（回滚）
            //超时处理
        }
    }

    private static class MyTask {

        String name = null;
        int m = 0;
        int result = 0; // 0 失败 1 成功 2 取消
        volatile boolean cancelled = false;
        volatile boolean cancelling = false;


        MyTask(String name, int m, int result) {
            this.name = name;
            this.m = m * 1000;
            this.result = result;
        }

        int call() {
            int interval = 100;
            int total = 0;
            try {
                for (; ; ) {
                    Thread.sleep(interval); //cpu密集型 做一些计算
                    total += interval;
                    if (total > m) break;
                    if (cancelled) return 2;//cancelled 隔一段时间检查一次safe point
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.name + "  end");
            return this.result;
        }

        void cancel() {
            //DCL
            if (!cancelled) {
                synchronized (this) {//避免多次
                    if (cancelled) return;
                    cancelling = true;//主要做一个标志位，避免重复取消这些
                    System.out.println(this.name + "  cancelling");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);//处理一些任务 回滚什么的
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(this.name + "  cancelled");
                    cancelled = true;
                }
            }
        }
    }
}
