package array;

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/28
 * @Description:
 */
public class MinSubArrayLen {

    public static void main(String[] args) {
        new MinSubArrayLen().minSubArrayLen(7,new int[]{
                2,3,1,2,4,3
        });
    }

    public int minSubArrayLen2(int s, int[] nums) {
        //双指针
        int left = 0;
        int right = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        while (right < nums.length) {
            sum += nums[right];
            while (sum >= s) {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }


    public int minSubArrayLen(int s, int[] nums) {
        //前缀和+二分
        int[] pre = new int[nums.length+1];
        int sum = 0;
        for(int i=0;i<nums.length;i++) {
            sum += nums[i];
            pre[i+1] = sum;
        }
        //前缀和必然递增

        int min = Integer.MAX_VALUE;
        for(int i=1;i<nums.length;i++){
            int target = s + pre[i-1];//要查找的元素应该是 pre[bound] - pre[i-1] >= s
            int bound = Arrays.binarySearch(pre, target);//通过二分查找该元素
            if (bound < 0) {
                // 二分查找中 找不到的话返回值为小于目标值的界限即 return -(low + 1);  // key not found.
                bound = -bound - 1;
            }
            if (bound <= nums.length) {
                min = Math.min(min, bound - (i - 1));
            }
        }

        return min==Integer.MAX_VALUE?0:min;
    }
}
