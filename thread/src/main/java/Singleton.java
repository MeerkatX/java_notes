/**
 * @ClassName: Singleton
 * @Auther: MeerkatX
 * @Date: 2020-09-22 09:50
 * @Description: double check singleton
 */
public class Singleton {

    private static volatile Singleton singleton = null;

    private Singleton(){}

    //需要static
    public static Singleton getInstance(){
        if (singleton==null){
            synchronized (Singleton.class){//这里锁的是类对象
                if (singleton==null){
                    singleton = new Singleton();
                    /*
                        instance = new Singleton() 分为：1-分配内存空间，2-初始化对象，3-设置instance指向内存地址；
                        指令重排序：
                        单线程环境下，由于2-3没有数据依赖关系，故可能存在1-2-3 以及 1-3-2两种情况；
                        所以如果没有volatile的话，最外层可能获得的是未初始化完全的对象
                        所以在这里加入volatile来防止指令重排序。
                     */
                }
            }
        }
        return singleton;
    }
}
