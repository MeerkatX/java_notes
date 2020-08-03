package bytedance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/17
 * @Description:
 */
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int m = in.nextInt();
        HashMap<Character, List<Integer>> set = new HashMap<>();
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (set.containsKey(cs[i])) {
                set.get(cs[i]).add(i);
            } else {
                List<Integer> l = new ArrayList<>();
                l.add(i);
                set.put(cs[i], l);
            }
        }


    }
}
