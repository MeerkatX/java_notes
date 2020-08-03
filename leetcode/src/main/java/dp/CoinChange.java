package dp;

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/11
 * @Description:
 */
public class CoinChange {

    public static void main(String[] args) {
        System.out.println(new CoinChange().coinChange(new int[]{
                2
        }, 3));
    }

    public int coinChange(int[] coins, int amount) {

/* 做了优化：
        if (coins.length == 0) return -1;
        if (amount == 0) return 0;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp,amount+1);
        dp[0]=0;
        for (int i = 0; i < amount + 1; i++) {
            for (int c = 0; c < coins.length; c++) {
                if (i-coins[c]<0) continue;
                dp[i] = Math.min(dp[i], dp[i - coins[c]] + 1);
            }
        }
        return dp[amount] == (amount+1) ? -1 : dp[amount];
        */





        if (coins.length == 0) return -1;
        if (amount == 0) return 0;
        Arrays.sort(coins);
        if (coins[0] > amount) return -1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp,amount);
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] > amount) break;
            dp[coins[i]] = 1;
        }
        for (int i = 1; i < amount + 1; i++) {
            for (int c = 0; c < coins.length; c++) {
                if (coins[c] >= i) break;
                dp[i] = Math.min(dp[i], dp[i - coins[c]] + 1);
            }
        }
        return dp[amount] == amount ? -1 : dp[amount];
    }
}
