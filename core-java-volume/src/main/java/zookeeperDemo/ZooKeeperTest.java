package zookeeperDemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/20
 * @Description:
 */
@Slf4j
public class ZooKeeperTest {


    private static final String SERVER_HOST = "127.0.0.1:2181";

    private static final int SESSION_TIME_OUT = 2000;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER_HOST, SESSION_TIME_OUT, (watchedEvent) -> {
            Watcher.Event.KeeperState state = watchedEvent.getState();
            if (Watcher.Event.KeeperState.SyncConnected == state) {
                Watcher.Event.EventType type = watchedEvent.getType();
                if (Watcher.Event.EventType.None == type) {
                    System.out.println("zk客户端已连接...");
                }
            }
        });
        //zooKeeper.create("/java", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        log.info("----------------------------------------------------------");

        Stat stat = new Stat();
        byte[] bytes = zooKeeper.getData("/java", false, stat);
        log.info("ZNode: " + new String(bytes));
        log.info("dataVersion: " + stat.getVersion());

        zooKeeper.close();

    }


}
