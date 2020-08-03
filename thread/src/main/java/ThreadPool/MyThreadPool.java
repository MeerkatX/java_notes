package ThreadPool;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/3
 * @Description:
 */
public interface MyThreadPool {

    void execute(Runnable job);

    void shutdown();

    void addWorkers(int num);

    void removeWorkers(int num);

    int getJobSize();
}
