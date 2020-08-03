package dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/14
 * @Description:
 */
public class GenerateParenthesis {

    public static void main(String[] args) {
        List<String> ans = new GenerateParenthesis().generateParenthesis(1);
        ans.stream().forEach(System.out::println);
    }

    private List<String> ans = new ArrayList<>();


    public List<String> generateParenthesis(int n) {
        generateParenthesisHelper(0, 0, n, new StringBuilder());
        return ans;
    }

    public void generateParenthesisHelper(int l, int r, int n, StringBuilder one) {
        if (l == n) {
            for (int i = r; i < n; i++) one.append(")");
            ans.add(one.toString());
            for (int i = r; i < n; i++) one.deleteCharAt(one.length()-1);
            return;
        }
        if (l == r) {
            one.append('(');
            generateParenthesisHelper(l + 1, r, n, one);
            one.deleteCharAt(one.length()-1);
            return;
        }
        if (l > r && l < n) {
            one.append(")");
            generateParenthesisHelper(l, r + 1, n, one);
            one.deleteCharAt(one.length()-1);
            one.append("(");
            generateParenthesisHelper(l + 1, r, n, one);
            one.deleteCharAt(one.length()-1);
        }
    }
}
