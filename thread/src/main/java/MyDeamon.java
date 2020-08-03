

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/22
 * @Description: 守护线程
 */
public class MyDeamon {

    public static void main(String[] args) throws InterruptedException {
        Thread deamon = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i=0;i<1000;i++){
                        System.out.println("hello");
                    }
                }finally {
                    System.out.println("fin");
                }
            }
        });
        deamon.setDaemon(true);
        deamon.start();
        Thread.sleep(10);
    }
}
