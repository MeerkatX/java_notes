package slidingwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/16
 * @Description:
 */
public class FindAnagrams {
    public List<Integer> findAnagrams(String s, String p) {

        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();
        for (char c : p.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);
        int left = 0;
        int right = 0;
        int size = 0;
        List<Integer> ans = new ArrayList<>();
        while (right < s.length()) {
            char c = s.charAt(right++);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) size++;
            }
            // 当维持的窗口大于 s2.length时，说明需要滑动了
            // 与minWindow不同的是 minWindow是当所有的需要目标都满足则滑动窗口（窗口是不固定的）
            // 本题窗口是固定的 即s2.length()
            while (right - left >= p.length()) {
                if (size == need.size()) ans.add(left);
                char d = s.charAt(left++);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) size--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return ans;
    }
}
