import ThreadPool.DefaultThreadPool;
import ThreadPool.MyThreadPool;
import org.junit.Test;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/3
 * @Description:
 */
public class MyThreadPoolTest {

    @Test
    public void test() {
        MyThreadPool threadPool = new DefaultThreadPool();
        threadPool.execute(() -> {
            while (true) {
                System.out.println("my pool");
            }
        });
    }
}
