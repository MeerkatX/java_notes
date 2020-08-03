package dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/26
 * @Description:
 */
public class GrayCode {

    public static void main(String[] args) {
        new GrayCode().grayCode(2).forEach(System.out::println);
    }

    public List<Integer> grayCode(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        int num = 1 << n;
        for (int i = 0; i < num; i++) {
            res.add(i >> 1 ^ i);
        }
        return res;
    }
}
