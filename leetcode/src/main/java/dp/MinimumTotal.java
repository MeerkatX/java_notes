package dp;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/5
 * @Description: 120. 三角形最小路径和
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 */
public class MinimumTotal {
    public static void main(String[] args) {
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(new ArrayList<Integer>() {{
            add(2);
        }});
        triangle.add(new ArrayList<Integer>() {{
            add(3);
            add(4);
        }});
        triangle.add(new ArrayList<Integer>() {{
            add(6);
            add(5);
            add(7);
        }});
        triangle.add(new ArrayList<Integer>() {{
            add(4);
            add(1);
            add(8);
            add(3);
        }});
        System.out.println(new MinimumTotal().minimumTotal2(triangle));
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        //自顶向下
        int n = triangle.size();
        int[] dp = new int[n];
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            int rn = triangle.get(i).size();
            int pre = dp[0];
            for (int j = 0; j < rn; j++) {
                int t = dp[j];
                if (j == (rn - 1)) dp[j] = pre + triangle.get(i).get(j);
                else dp[j] = Math.min(dp[j], pre) + triangle.get(i).get(j);
                pre = t;
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) min = Math.min(min, dp[i]);
        return min;
    }

    public int minimumTotal2(List<List<Integer>> triangle) {
        //自底向上 反推
        int n = triangle.size();
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) dp[i] = triangle.get(n - 1).get(i);
        for (int i = n-2; i >= 0; i--) {
            for(int j = 0; j< triangle.get(i).size();j++){
                dp[j] = Math.min(dp[j],dp[j+1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    public int minimumTotal3(List<List<Integer>> triangle) {
        //自底向上 通过多加一行 int[] dp = new int[triangle.size() + 1];赋值操作合并到动态规划中

        // 特判
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        // dp中记录了求第i行时，第i+1的最小路径和
        int[] dp = new int[triangle.size() + 1];

        for (int i = triangle.size() - 1; i >= 0; i--) {
            List<Integer> rows = triangle.get(i);
            for (int j = 0; j < rows.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + rows.get(j);
            }
        }
        return dp[0];
    }
}
