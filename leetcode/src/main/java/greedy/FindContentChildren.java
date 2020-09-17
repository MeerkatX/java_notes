package greedy;

import java.util.Arrays;

/**
 * @ClassName: FindContentChildren
 * @Auther: MeerkatX
 * @Date: 2020-09-16 21:00
 * @Description: 455. 分发饼干
 * https://leetcode-cn.com/problems/assign-cookies/
 */
public class FindContentChildren {

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        for (int i = 0, p = 0; i < g.length && p < s.length; p++ ) {
            if (s[p] >= g[i]) {
                i++;
                count++;
            }
        }
        return count;
    }

}
