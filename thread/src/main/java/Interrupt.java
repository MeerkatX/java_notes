import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/16
 * @Description:
 */
public class Interrupt {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Thread thread = new Thread(()->{
            try {
//                Selector selector = Selector.open();
//                selector.select();//select会响应中断立即返回 0 所以可以通过中断来结束线程
//                System.out.println("中断");

                //除此之外阻塞IO无法响应中断，一般通过关闭通道、流等关闭线程（待议）
//                int in = 0;
//                System.in.read();
//                while (!Thread.currentThread().isInterrupted() && (in = System.in.read())!=0) System.out.println("hh");
//                System.out.println("阻塞");
                Object o= new Object();
                synchronized (o){
                    //wait和notify必须和synchronized一同使用，不然会抛异常

                    o.wait();//调用o.wait 进入阻塞让出cpu WAITING 状态

                }
                //这种阻塞对于thread来说是 TIMED_WAITING 状态
                Thread.sleep(1000);
                System.out.println("===========wake up=============");
                Thread.yield();
                System.out.println("=========== end ================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ///////////////////////////////////////////////////

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 4, 60,
                        TimeUnit.MINUTES, new LinkedBlockingDeque<>());
        FutureTask<String> futureTask = new FutureTask(new Callable() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });
        new Thread(futureTask).start();

        System.out.println(futureTask.get());

        //////////////////////////////////////////////////

        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        Thread.sleep(100);
        System.out.println(thread.getState());
//        thread.interrupt();
//        System.out.println(thread.getState());
    }


}
