package dfs;

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/28
 * @Description:
 */
public class NumSubseq {
    public static void main(String[] args) {
        int[] arr = new int[]{
                14, 4, 6, 6, 20, 8, 5, 6, 8, 12, 6, 10, 14, 9, 17, 16, 9, 7, 14, 11, 14, 15, 13, 11, 10, 18, 13, 17, 17, 14, 17, 7, 9, 5, 10, 13, 8, 5, 18, 20, 7, 5, 5, 15, 19, 14
        };
        System.out.println(new NumSubseq().numSubseq(arr, 22));
    }

    public int numSubseq(int[] nums, int target) {
       /*
        double ans = 0;
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            while (nums[left] + nums[right] > target) {
                right--;
            }
            if (right >= left) ans += Math.pow(2, right - left) % (1e9+7);
            left++;
        }
        return (int) ans;
        */
        Arrays.sort(nums);
        if (nums[0] * 2 > target) {
            return 0;
        }
        int left = 0;
        int right = nums.length - 1;
        int res = 0;
        int[] pow = new int[nums.length];
        pow[0] = 1;
        int mode = 1_0000_0000_7;
        for (int i = 1; i < nums.length; i ++) {
            pow[i] = pow[i-1] * 2;
            pow[i] %= mode;
        }
        while (left <= right) {
            if (nums[left] + nums[right] <= target) {
                res += pow[right - left];//每次加 2^n 取余
                res %= mode;//之后再取余
                left ++;
            }
            else {
                right --;
            }
        }
        return res;
    }
/*
    public int numSubseq(int[] nums, int target) {
        Arrays.sort(nums);
        dfs(nums,0,target,Integer.MAX_VALUE,Integer.MIN_VALUE);
        return (int)((ans-1) % ( 1000_000_000 + 7 ));
    }

    public void dfs(int[] nums,int start, int target, int min,int max){
        if(min+max>target) return;
        ans++;
        int tmin = min;
        int tmax = max;
        for(int i = start;i<nums.length;i++){
            min = Math.min(min,nums[i]);
            max = Math.max(max,nums[i]);
            dfs(nums,i+1,target,min,max);
            min = tmin;
            max = tmax;
        }
    }
*/
}
