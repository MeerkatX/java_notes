package Future;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/13
 * @Description:
 */
public class RunnableDemo implements Runnable {

    @Override
    public void run() {
        for (; ; ) {
            try {
                System.out.println("runnable");
                Thread.sleep(1000);
                /*
                sleep() 可能会抛出 InterruptedException。因为异常不能跨线程传播回 main() 中，
                因此必须在本地进行处理。线程中抛出的其它异常也同样需要在本地进行处理。
                 */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
