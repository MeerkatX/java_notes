package sort;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/27
 * @Description:
 */
public class FindKthLargest {
    public static void main(String[] args) {
        System.out.println(new FindKthLargest().findKthLargest2(new int[]{
                3, 2, 3, 1, 2, 4, 5, 5, 6
        }, 4));

        System.out.println(new FindKthLargest().findKthLargest2(new int[]{
                3, 3, 3, 3, 3, 3, 3, 3, 3
        }, 4));
    }

    public int findKthLargest(int[] nums, int k) {
        //利用api优先队列 或 Arrays.sort()
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int i : nums) {
            pq.add(i);
        }
        for (int i = 0; i < k - 1; i++) {
            pq.poll();
        }
        int ans = pq.poll();
        return ans;
    }


    ///////////////////////////////////////快速选择 k （基于快排中划分思想）///////////////////////////////////////////


    public int findKthLargest2(int[] nums, int k) {
        //快速选择
        //类似快速排序 找位置为nums.length - k的数
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] nums, int l, int r, int index) {
        int p = randomPartition(nums, l, r);
        if (p < index) return quickSelect(nums, p + 1, r, index);
        else if (p > index) return quickSelect(nums, l, p - 1, index);
        else return nums[p];
    }

    Random random = new Random();

    public int randomPartition(int[] a, int L, int R) {
        int i = random.nextInt(R - L + 1) + L; //范围 [ 0 , R - L + 1 ) 再 + L
        swap(a, i, R);//随机选取 一个数为基准数（放到尾部）
        return partition(a, L, R);//标准划分流程
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public int partition(int[] a, int l, int r) {
        int x = a[r], i = l, j = r - 1;
        while (true) {
            while (i < r && x >= a[i]) i++;
            while (j > l && x <= a[j]) j--;
            if (i >= j) break;
            swap(a, i, j);
        }
        swap(a, i, r);
        return i;
    }
}
