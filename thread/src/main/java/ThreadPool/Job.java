package ThreadPool;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/3
 * @Description:
 */
public class Job implements Runnable {
    @Override
    public void run() {
        System.out.println("job");
    }
}
