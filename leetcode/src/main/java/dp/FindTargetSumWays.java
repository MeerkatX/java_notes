package dp;

/**
 * @ClassName: FindTargetSumWays
 * @Auther: MeerkatX
 * @Date: 2020-09-08 15:19
 * @Description: 494. 目标和
 * https://leetcode-cn.com/problems/target-sum/
 */
public class FindTargetSumWays {

    public static void main(String[] args) {
        new FindTargetSumWays().findTargetSumWays(new int[]{
                1, 1, 1, 1, 1
        }, 3);
    }

    public int findTargetSumWays(int[] nums, int S) {
        int len = nums.length;

        int sum = 0;
        for (int num : nums) sum += num;

        int t = sum * 2 + 1; //从负到正
        //int[][] dp = new int[len + 1][S + 1];//因为其实还有负数的可能性
        int[][] dp = new int[len][t];

        // 初始化
        if (nums[0] == 0) {
            // 因为如果nums[0]==0那么dp[0][sum]需要初始化为2，因为加减0都得0。
            dp[0][sum] = 2;
        } else {
            //初始条件，第一个数 正负两种情况
            dp[0][sum + nums[0]] = 1;
            dp[0][sum - nums[0]] = 1;
        }

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < t; j++) {
                //如果超过范围，也就不考虑了
                if (j + nums[i] < t) dp[i][j] += dp[i - 1][j + nums[i]];
                if (j - nums[i] >= 0) dp[i][j] += dp[i - 1][j - nums[i]];
            }
        }
        return dp[len - 1][sum + S];
    }

}
