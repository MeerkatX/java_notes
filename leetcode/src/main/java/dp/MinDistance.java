package dp;

/**
 * @ClassName: MinDistance
 * @Auther: MeerkatX
 * @Date: 2020-09-07 22:24
 * @Description: 72. 编辑距离
 * 动态规划
 * https://leetcode-cn.com/problems/edit-distance/
 */
public class MinDistance {

    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        //初始条件
        for (int i = 1; i < len1 + 1; i++) dp[i][0] = i;
        for (int i = 1; i < len2 + 1; i++) dp[0][i] = i;

        //计算
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];//什么都不做
                }else {
                    //修改，选最小的
                    dp[i][j] = Math.min(Math.min(dp[i-1][j]+1,dp[i][j-1]+1),dp[i-1][j-1]+1);
                }
            }
        }

        return dp[len1][len2];
    }
}
