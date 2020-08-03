package dp;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/21
 * @Description: 回文子串
 */
public class CountSubstrings {

    public int countSubstrings(String s) {
        char[] chars = s.toCharArray();
        boolean[][] dp = new boolean[chars.length][chars.length];
        int ans = 0;
        for (int i = 0; i < chars.length; i++) {
            dp[i][i] = true;
            ans++;
        }
        for (int j = 1; j < chars.length; j++) {
            for (int i = 0; i < j; i++) {
                if (chars[i] == chars[j]) {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j]) ans++;
            }
        }
        return ans;
    }
}
