

import java.util.Stack;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/27
 * @Description:
 */
public class MinAddToMakeValid {
    public int minAddToMakeValid(String S) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            if (stack.isEmpty())
                stack.push(S.charAt(i));
            else {
                char c = stack.peek();
                if (c == ')')
                    stack.push(S.charAt(i));
                else if (S.charAt(i) == ')')
                    stack.pop();
                else
                    stack.push(S.charAt(i));
            }
        }
        int num = 0;
        while (!stack.isEmpty()) {
            num++;
            stack.pop();
        }
        return num;
    }

    public int minAddToMakeValid1(String S) {
        int left = 0;
        int right = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                left++;
            } else {
                if (left > 0)
                    left--;
                else
                    right++;
            }
        }
        return right + left;
    }
}
