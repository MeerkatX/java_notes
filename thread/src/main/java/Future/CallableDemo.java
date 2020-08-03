package Future;

import java.util.concurrent.Callable;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/13
 * @Description:
 */
public class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        try {
            System.out.println("callable");
            Thread.sleep(1000);
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 2;
        }
    }
}
