package lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/27
 * @Description:
 */
public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        //这里涉及一个许可证问题
        System.out.println("begin park");
        //使当前线程获得许可证
        LockSupport.unpark(Thread.currentThread());
        //再次调用park方法会立即返回
        LockSupport.park();
//        LockSupport.park(); //再再次调用就又阻塞了
        System.out.println("end park");

        System.out.println("=================================================");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("child thread begin park");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 因为park之后被唤醒是不带有原因的，
                // 所以根据调用park的原因，再次检查是否满足，不满足继续park
                while (!Thread.currentThread().isInterrupted()) {
                    LockSupport.park();//调用park方法挂起自己
                    System.out.println("park end");
                }
                System.out.println("child thread unpark");
            }
        });
        //LockSupport.unpark(thread);//LockSupport只对开启的线程有效 放到这里是没用的

        thread.start();

        //LockSupport只对开启的线程有效 放到这里会有一次因为获取许可证而直接返回的park
        LockSupport.unpark(thread);

        Thread.sleep(2000);

        System.out.println("main thread unpark child");

        LockSupport.unpark(thread);//这样unpark是无法唤醒的 因为判断线程没有被中断，就又park了

        Thread.sleep(1000);

        System.out.println("child interrupt");

        thread.interrupt();//中断的同时也会唤醒park的线程


    }
}
