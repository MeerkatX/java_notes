package ForkJoinPool_;

import java.util.concurrent.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/8/4
 * @Description: ForkJoin的两个类：RecursiveTask（有返回值） 和 RecursiveAction（无返回值）
 */
public class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private long start;
    private long end;

    public SumTask(long n) {
        this(1, n);
    }

    public SumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if ((end - start) <= THRESHOLD) {
            for (long l = start; l < end; l++) {
                sum += l;
            }
        } else {
            //分治，递归
            long mid = (start + end) >>> 1;
            SumTask left = new SumTask(start, mid);
            SumTask right = new SumTask(mid + 1, end);
            left.fork(); //ForkJoinTask关键接口
            right.fork();
            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask sumTask = new SumTask(100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Long> future = forkJoinPool.submit(sumTask);
        Long r = future.get();
        forkJoinPool.shutdown();
        System.out.println(r);
    }
}
