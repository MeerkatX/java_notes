package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/6
 * @Description:
 */
public class GetPermutation {
    public String getPermutation(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) list.add(i);
        int div = 1;
        for (int i = 2; i < n; i++) div *= i;
        StringBuffer s = new StringBuffer();
        int n2 = n-1;
        for (int i = 0; i < n; i++) {
            int no = k / div;
            int noo = k % div;
            s.append(list.get(no));
            list.remove(no);
            k = noo;
            div /= n2;
            n2--;
        }
        return s.toString();
    }
}
