package dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GetPermutation
 * @Auther: MeerkatX
 * @Date: 2020-09-05 15:19
 * @Description: 60. 第k个排列
 */
public class GetPermutation {

    List<String> ans = new ArrayList<>();
    int num = 0;

    public String getPermutation(int n, int k) {
        dfs(n, k, "", 0, new int[n]);
        return ans.get(k - 1);
    }

    //没有减枝 计算 n-1的阶乘 可以减枝优化
    /*
    执行用时：1242 ms, 在所有 Java 提交中击败了7.34%的用户
    内存消耗：73.7 MB, 在所有 Java 提交中击败了7.18%的用户
     */
    public void dfs(int n, int k, String res, int deep, int[] flag) {
        if (deep == n) {
            ans.add(res);
            num += 1;
            return;
        }
        if (num != k) {
            for (int i = 0; i < n; i++) {
                if (flag[i] == 1) continue;
                flag[i] = 1;
                dfs(n, k, res + (i + 1), deep + 1, flag);
                flag[i] = 0;
            }
        }
    }

    public String getPermutationO(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) list.add(i);
        int div = 1;
        for (int i = 2; i < n; i++) div *= i;
        StringBuffer s = new StringBuffer();
        int n2 = n-1;
        k-=1;
        for (int i = 0; i < n; i++) {
            int no = k / div;
            int noo = k % div;
            s.append(list.remove(no));
            k = noo;
            if(div != 1){
                div /= n2;
                n2--;
            }
        }
        return s.toString();
    }
}
