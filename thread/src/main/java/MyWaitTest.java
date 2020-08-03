/**
 * @Auther: 徐少伟
 * @Date: 2020/6/3
 * @Description:
 */
public class MyWaitTest {
    class Inner {
        public void tt() {
            testWait();
        }
    }

    public void testWait() {
        System.out.println("start---");
        try {
            wait(1_0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end---");
    }

    public void testNonSync(){
        System.out.println("其他非同步方法");
    }


    public synchronized void testSync1(){
        System.out.println("同步方法1");
    }

    public synchronized void testSync2(){
        System.out.println("同步方法2");
    }

    public static void main(String[] args) throws InterruptedException {
        MyWaitTest test = new MyWaitTest();
        Thread thread1 = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("th1");
            }
        });
        Thread thread2 = new Thread(test::testSync2);
        thread1.start();
        Thread.sleep(10);
        thread1.interrupt();
        thread2.start();
    }

/*
    public static void main(String[] args) {

        MyWaitTest test = new MyWaitTest();
        //内部类要引用局部变量必须是final或者事实上的final
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (test) {
                    test.testWait();

                }
            }
        }).start();
        //如果test赋的值变了，就不构成事实上的final 无法编译
//        test = null;
    }
*/
}
