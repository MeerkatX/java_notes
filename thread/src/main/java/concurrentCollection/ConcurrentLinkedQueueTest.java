package concurrentCollection;

import javafx.util.Pair;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/19
 * @Description:
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) throws InterruptedException {

        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        LinkedBlockingQueue<Object> clq = new LinkedBlockingQueue<>();
        DelayQueue<DelayItem<Pair<String,String>>> delayQueue = new DelayQueue<>();
        delayQueue.offer(new DelayItem<>(new Pair<>("a","av"),10000));
        delayQueue.offer(new DelayItem<>(new Pair<>("b","bv"),2000));
        delayQueue.offer(new DelayItem<>(new Pair<>("c","cv"),100));
        System.out.println(delayQueue.take().getItem().getKey());
        System.out.println(delayQueue.take().getItem().getKey());
        System.out.println(delayQueue.take().getItem().getKey());

        int i =0;
        retry:
        for (;;){
            if (i==10)
                break retry;
            i++;
        }

        System.out.println("success");
    }
}
