package sort;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/23
 * @Description: 优先队列 堆(完全二叉树)
 */
public class MaxPQ {
    private int[] pq = new int[16];
    private int N = 0; //用来保存队列中有多少元素

    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue();
        MaxPQ maxPQ = new MaxPQ();
        maxPQ.insert(4);
        maxPQ.insert(2);
        maxPQ.insert(7);
        maxPQ.insert(1);
        maxPQ.insert(9);
        maxPQ.insert(100);
        maxPQ.insert(13);
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
    }

    public void insert(int i) {
        pq[++N] = i;
        swim(N);
    }

    public int delMax() {
        swap(N, 1);
        int max = pq[N];
        pq[N] = 0;
        --N;
        sink(1);
        return max;
    }


    private void swim(int k) {
        while (k > 1 && pq[k] > pq[k / 2]) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k < N) {
            int j = 2 * k;
            if (j < N && pq[j] > pq[j + 1])
                j++;
            if (pq[j] > pq[k])
                swap(j, k);
            else
                break;
            k = j;
        }
    }

    private void swap(int i, int j) {
        int t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

}
