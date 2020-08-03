package Future;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/13
 * @Description:
 */
public class RunnableDemo implements Runnable {

    @Override
    public void run() {
        while (! Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("running");
                Thread.sleep(1000);
                /*
                sleep() 可能会抛出 InterruptedException。因为异常不能跨线程传播回 main() 中，
                因此必须在本地进行处理。线程中抛出的其它异常也同样需要在本地进行处理。
                 */
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().isInterrupted());//sleep方法会在抛出中断异常前重置中断标志位
                break;
            }
        }
    }
}
