package ThreadLocal;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 */
public class TreadLocal1 {
    /*
    为什么Entry要使用弱引用

    若是强引用，即使tl = null，但key的引用依然指向ThreadLocal对象，所以会有内存泄漏，而使用弱引用不会
    但是还是会有内存泄漏存在，ThreadLocal被回收时，key的值变为null，导致整个value再也无法被访问到因此依然存在
    内存泄漏
     */

    static ThreadLocal<Person> tl = new ThreadLocal<>();//只在本线程存在

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());//其他线程set 这边是get不到的
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());//set只在本线程 Entry WeakReference
        }).start();
    }

    static class Person {
        String name = "zzz";
        int age = 1;
    }
}
