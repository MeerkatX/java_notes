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

    static ThreadLocal<Person> tl = new ThreadLocal<>();//只在本线程存在,每个线程内部有一个ThreadLocalMap
    //以t1为键，值为set的值 map.set(this, value); this即t1自身
    //发生冲突，map采用再哈希法（开放地址法）：
    //     for (Entry e = tab[i];
    //      e != null;
    //      e = tab[i = nextIndex(i, len)]) {   ...   }

    //      具体nextIndex如下：
    //        private static int nextIndex(int i, int len) {
    //            return ((i + 1 < len) ? i + 1 : 0);
    //        }

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
            Person person = tl.get();
            System.out.println(person.age+"  "+person.name);
        }).start();
    }

    static class Person {
        String name = "zzz";
        int age = 1;
    }
}
