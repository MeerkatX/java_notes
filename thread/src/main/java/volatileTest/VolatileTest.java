package volatileTest;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/1
 * @Description:
 */
public class VolatileTest {
    //volatile static boolean flag = true;
    static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("in");
            while (flag) ;
            System.out.println("out");
        }).start();


        try {
            Thread.sleep(1000);

            flag = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
