package dp;

import list.ListNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/31
 * @Description:
 */
public class WordBreak {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>() {{
            add("cats");
            add("dog");
            add("sand");
        }};

        new WordBreak().wordBreak("catsandog", list);
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        int min = Integer.MAX_VALUE;
        HashSet<String> hashSet = new HashSet<>(wordDict);
        for (String w : wordDict) {
            min = Math.min(w.length(), min);
        }
        int count = 0;
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 0; i < s.length(); i++) {
            count++;
            if (count < min) continue;
            for(int j = i; j<s.length(); j++){
                if (dp[j] && hashSet.contains(s.substring(i,j))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];

    }


}
