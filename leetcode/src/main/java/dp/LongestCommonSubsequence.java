package dp;

/**
 * @ClassName: LongestCommonSubsequence
 * @Auther: MeerkatX
 * @Date: 2020-09-08 10:40
 * @Description: 1143. 最长公共子序列
 * 动态规划
 * https://leetcode-cn.com/problems/longest-common-subsequence/
 */
public class LongestCommonSubsequence {

    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始条件 dp[0][i] = 0; dp[i][0] = 0;
        //状态转移
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;//如果在lcs中，同时向前移动并加一
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);//如果不在lcs中，分别移动
                //i-1 和 j-1 两种情况
            }
        }
        return dp[len1][len2];
    }
}
