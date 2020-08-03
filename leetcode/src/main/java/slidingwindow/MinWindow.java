package slidingwindow;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/16
 * @Description: 76. 最小覆盖子串
 */
public class MinWindow {

    public String minWindow(String s, String t) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1); //初始化我需要几个
        int left = 0;
        int right = 0;
        int valid = 0;
        int min = Integer.MAX_VALUE;
        int start = 0;
        while (right < s.length()) {
            char c = s.charAt(right++);
            if (need.containsKey(c)) {
                //对于需要的元素 将该元素塞进去
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) valid++; // 如果已经满足需要了，就加一
            }
            //当目前窗口元素都满足需要之后，需要缩窗口（窗口尺寸是变化的）
            while (valid == need.size()) {
                //先维护最小窗口
                if (right - left < min) {
                    start = left;
                    min = right - left;
                }
                // 从左到右搜索，挨个判断该不该缩小
                // （这里与我一开始想的不同，我一开始认为可以保存位置信息，这样就能直接往将left 变为最前的位置）
                char d = s.charAt(left++);
                if (need.containsKey(d)) {
                    //搜索到了之后 就去掉1个那么valid--
                    if (window.get(d).equals(need.get(d))) valid--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);//最后如果等于最大值，肯定没有全包，否则输出相应
    }

}
