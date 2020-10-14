package dp;

/**
 * @ClassName: LengthOfLIS
 * @Auther: MeerkatX
 * @Date: 2020-10-12 20:57
 * @Description: https://leetcode-cn.com/problems/longest-increasing-subsequence/
 * 300. 最长上升子序列
 */
public class LengthOfLIS {

    public static void main(String[] args) {
        System.out.println(new LengthOfLIS().lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));
    }

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
