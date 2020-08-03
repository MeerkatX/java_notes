package dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/29
 * @Description:
 */
public class RestoreIpAddresses {
    private List<String> ans = new ArrayList<>();

    public static void main(String[] args) {
        new RestoreIpAddresses().restoreIpAddresses("25525511135").forEach(System.out::println);
    }

    public List<String> restoreIpAddresses(String s) {
        dfs(0, s, "", 0);
        return ans;
    }

    private void dfs(int deep, String s, String t, int start) {
        if (deep == 4) {
            if (t.length() - 4 == s.length()) {
                ans.add(t.substring(1));
            }
            return;
        }

        int num = 0;
        for (int i = start; i < s.length(); i++) {
            num = num * 10 + (s.charAt(i) - 48);
            if (num > 255) return;
            String in = t + "." + num;
            dfs(deep + 1, s, in, i + 1);
        }
    }


    ////////////////////////////////减枝/////////////////////////////
    //  字符串的长度小于 4 或者大于 12 ，
    // 一定不能拼凑出合法的 ip 地址（这一点可以一般化到中间结点的判断中，以产生剪枝行为）；

    public List<String> restoreIpAddresses2(String s) {
        int len = s.length();
        List<String> res = new ArrayList<>();
        if (len > 12 || len < 4) {
            return res;
        }

        Deque<String> path = new ArrayDeque<>(4);
        dfs(s, len, 0, 4, path, res);
        return res;
    }

    // 需要一个变量记录剩余多少段还没被分割

    private void dfs(String s, int len, int begin, int residue, Deque<String> path, List<String> res) {
        if (begin == len) {
            if (residue == 0) {
                res.add(String.join(".", path));
            }
            return;
        }

        for (int i = begin; i < begin + 3; i++) {
            if (i >= len) {
                break;
            }

            if (residue * 3 < len - i) {
                continue;
            }

            if (judgeIpSegment(s, begin, i)) {
                String currentIpSegment = s.substring(begin, i + 1);
                path.addLast(currentIpSegment);

                dfs(s, len, i + 1, residue - 1, path, res);
                path.removeLast();
            }
        }
    }

    private boolean judgeIpSegment(String s, int left, int right) {
        int len = right - left + 1;
        if (len > 1 && s.charAt(left) == '0') {
            return false;
        }

        int res = 0;
        while (left <= right) {
            res = res * 10 + s.charAt(left) - '0';
            left++;
        }

        return res >= 0 && res <= 255;
    }
}
