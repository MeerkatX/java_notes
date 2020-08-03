package slidingwindow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/22
 * @Description:
 */
public class MaxSlidingWindow {


    public static void main(String[] args) {
        Arrays.stream(new MaxSlidingWindow().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)).forEach(System.out::println);
    }

    //动态规划
    public int[] maxSlidingWindowDp(int[] nums, int k) {
        int n = nums.length;

        if (n * k == 0) return new int[0];
        if (k == 1) return nums;


        int[] left = new int[n];
        left[0] = nums[0];
        int[] right = new int[n];
        right[n - 1] = nums[n - 1];

        for (int i = 1; i < n; i++) {
            //将数组分为 n/k 段 求每段的最大值
            if (i % k == 0) left[i] = nums[i]; //从左往右第一个等于自身
            else left[i] = Math.max(nums[i], left[i - 1]);//保存每个块中的最大值

            if ((n - i) % k == 0) right[n - i - 1] = nums[n - i - 1];//从右向左
            else right[n - i - 1] = Math.max(nums[n - i - 1], right[n - i]);
        }
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < ans.length; i++) {
            /*
            两数组一起可以提供两个块内元素的全部信息。
            考虑从下标 i 到下标 j的滑动窗口。
            根据定义，right[i] 是左侧块内的最大元素，
            left[j] 是右侧块内的最大元素。因此滑动窗口中的最大元素为 max(right[i], left[j])。
            链接：https://leetcode-cn.com/problems/sliding-window-maximum/solution/hua-dong-chuang-kou-zui-da-zhi-by-leetcode-3/
             */
            ans[i] = Math.max(left[i + k - 1], right[i]);
        }
        return ans;
    }

    //双端队列
    ArrayDeque<Integer> deq = new ArrayDeque<Integer>();//队列保存窗口中最大元素的位置
    int[] nums;

    public int[] maxSlidingWindowDeque(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];
        if (k == 1) return nums;

        // init deque and output
        this.nums = nums;
        int max_idx = 0;
        for (int i = 0; i < k; i++) {
            clean_deque(i, k);
            deq.addLast(i);
            // compute max in nums[:k]
            if (nums[i] > nums[max_idx]) max_idx = i;
        }
        int[] output = new int[n - k + 1];
        output[0] = nums[max_idx];

        // build output
        for (int i = k; i < n; i++) {
            clean_deque(i, k);
            deq.addLast(i);
            output[i - k + 1] = nums[deq.getFirst()];
        }
        return output;


    }

    public void clean_deque(int i, int k) {
        // 如果头(当前最大值)已经超过滑动窗口边界的话，去掉头
        if (!deq.isEmpty() && deq.getFirst() == i - k) deq.removeFirst();

        // remove from deq indexes of all elements
        // which are smaller than current element nums[i]
        // 维持一个单调队列(从大到小)，队列中头最大，当要压入队列的数大于前一个，那么就弹出
        while (!deq.isEmpty() && nums[i] > nums[deq.getLast()]) deq.removeLast();
    }

    //优化了的暴力法
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        HashMap<Integer, Integer> max = new HashMap<>();
        int left = 0, right = k - 1;
        for (; right < nums.length; right++, left++) {
            if (!max.isEmpty() && left <= max.get(ans[left - 1]) && max.get(ans[left - 1]) <= right) {
                if (ans[left - 1] > nums[right]) {
                    ans[left] = ans[left - 1];
                } else {
                    ans[left] = nums[right];
                    max.put(nums[right], right);
                }
            } else {
                int t = Integer.MIN_VALUE;
                int p = 0;
                for (int i = 0; i < k; i++) {
                    if (nums[i + left] > t) {
                        t = nums[i + left];
                        p = i + left;
                    }
                }
                ans[left] = t;
                max.put(t, p);
            }
        }
        return ans;
    }
}
