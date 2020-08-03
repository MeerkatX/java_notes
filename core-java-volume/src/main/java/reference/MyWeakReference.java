package reference;

import java.lang.ref.WeakReference;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 */
public class MyWeakReference {
    public static void main(String[] args) {
        WeakReference<Object> o = new WeakReference<>(new Object());
        System.out.println(o.get());
        System.gc();
        System.out.println(o.get());

        ThreadLocal<Object> tl = new ThreadLocal<>();
        tl.set(new Object());
        tl.remove();//如果里面的对象不用的话(用完) 必须要remove掉，不然会有null键值对在tl的map中删不掉
    }
}
