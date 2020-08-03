package dp;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/29
 * @Description:
 */
public class Rob {


    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        int first = nums[0], second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        return second;


        // if(nums.length == 0) return 0;
        // int[] dp = new int[nums.length];
        // dp[0] = nums[0];
        // if(nums.length<2) return nums[0];
        // dp[1] = nums[1];
        // if(nums.length<3) return Math.max(dp[0],dp[1]);
        // dp[2] = nums[0]+nums[2];
        // int max = 0;
        // for(int i = 3; i< nums.length; i++){
        //     dp[i] = nums[i] + Math.max(dp[i-3],dp[i-2]);
        // }
        // return Math.max(dp[nums.length-1],dp[nums.length-2]);
    }
}
