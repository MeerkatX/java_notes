package dp;

/**
 * @ClassName: Change
 * @Auther: MeerkatX
 * @Date: 2020-09-07 16:30
 * @Description: 518. 零钱兑换 II
 * 完全背包问题
 * 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。
 * https://leetcode-cn.com/problems/coin-change-2/
 */
public class Change {
    public int change(int amount, int[] coins) {
        //优化，压缩为一维
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int x = coin; x < amount + 1; ++x) {
                dp[x] += dp[x - coin];
            }
        }
        return dp[amount];
    }

    public int changeDP(int amount, int[] coins) {
        // amount 即背包
        int len = coins.length;

        if(amount == 0) return 1;
        if(len == 0) return 0;

        int[][] dp = new int[len][amount + 1];//需要明确的一点，dp 存的是什么？
        // dp存的是答案，即组合数
        // 状态有两个 硬币 和 要凑的钱数 所以用二维dp数组
        // 初始状态
        for(int i=0; i< len; i++){
            dp[i][0] = 1;
        }
        for (int i =0; i< amount+1; i++){
            if(i % coins[0] == 0) dp[0][1] = 1;
        }

        for (int i = 1; i < coins.length; i++) {
            for (int j = 0; j < amount + 1; ++j) {
                if((j-coins[i]) >= 0)
                    dp[i][j] = dp[i-1][j] + dp[i][j-coins[i]];
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return dp[len - 1][amount];
    }
}
