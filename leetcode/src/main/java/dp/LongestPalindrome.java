package dp;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/21
 * @Description: 最长回文子串
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        new LongestPalindrome().longestPalindrome("ac");
    }

    // 动态规划
    public String longestPalindrome(String s) {
        if(s.length() < 2) return s;
        char[] chars = s.toCharArray();
        boolean[][] dp = new boolean[chars.length][chars.length];
        for (int i = 0; i < chars.length; ++i) {
            dp[i][i] = true;
        }
        int start = 0;
        int maxlen = 1;
        //dp[i][j]= (chars[i] == chars[j] && dp[i+1][j-1])
        for (int j = 1; j < chars.length; j++) {
            //按列遍历
            for (int i = 0; i < j; i++) {
                //再计算每一行
                if (chars[i] == chars[j]) {
                    if (j - i < 3) dp[i][j] = true;
                    else dp[i][j] = dp[i + 1][j - 1];
                }
                if (dp[i][j] && j - i + 1 > maxlen ) {
                    start = i;
                    maxlen = j - i + 1;
                }
            }
        }
        return s.substring(start, start + maxlen);
    }
}
