package slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/13
 * @Description:
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        LengthOfLongestSubstring lengthOfLongestSubstring = new LengthOfLongestSubstring();
        System.out.println(lengthOfLongestSubstring.lengthOfLongestSubstringSW2(" "));
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.equals(""))
            return 0;
        int max = 1;
        Map<Character, Integer> cm = new HashMap<>();
        for (int i = 0; i < s.length() - 1; ) {
            cm.put(s.charAt(i), i);
            for (int j = i + 1; j < s.length(); j++) {
                if (cm.containsKey(s.charAt(j))) {
                    i = cm.get(s.charAt(j)) + 1;
                    if (cm.size() > max)
                        max = cm.size();
                    cm.clear();
                    break;
                } else {
                    cm.put(s.charAt(j), j);
                    if (cm.size() > max)
                        max = cm.size();
                }
            }
        }
        return max;
    }

    //滑动窗口版
    public int lengthOfLongestSubstringSW(String s) {
        if (s.length() == 0)
            return 0;
        Map<Character, Integer> cm = new HashMap<>();
        int slow = 0;
        int max = 0;
        for (int fast = 0; fast < s.length(); fast++) {
            if (cm.containsKey(s.charAt(fast))) {
                slow = Math.max(slow, cm.get(s.charAt(fast)) + 1);
                //这里slow和字符s.charAt(fast)的位置的下一个比较，如果slow更大，说明已经把窗口缩到更前的位置了，
                //因为map这里存的是索引，所以可能前面就有重复
            }
            cm.put(s.charAt(fast), fast);
            max = Math.max(fast - slow + 1, max);
        }
        return max;
    }

    public int lengthOfLongestSubstringSW2(String s) {
        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        int maxsize = 0;
        while (right < s.length()) {
            char c = s.charAt(right++);
            window.put(c,window.getOrDefault(c,0)+1);
            while (window.get(c) > 1) {
                //缩小窗口 缩小到 c 如果c大于1 说明有重复，然后恢复window中元素数量 0
                char d = s.charAt(left++);
                window.put(d, window.get(d) - 1);
            }
            maxsize = Math.max(maxsize,right-left);
        }
        return maxsize;
    }
}
