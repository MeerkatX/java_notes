import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: BackspaceCompare
 * @Auther: MeerkatX
 * @Date: 2020-10-19 16:11
 * @Description:
 */
public class BackspaceCompare {

    public static void main(String[] args) {
        System.out.println(new BackspaceCompare().backspaceCompare("aaa###a", "aaaa###a"));
    }

    public boolean backspaceCompare(String S, String T) {
        System.out.println(realString(S));
        System.out.println(realString(T));
        return realString(S).equals(realString(T));
    }

    public boolean backspaceCompare2(String S, String T) {
        int i = S.length() - 1, j = T.length() - 1;
        int skipS = 0, skipT = 0;

        while (i >= 0 || j >= 0) {
            while (i >= 0) {
                if (S.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break;
                }
            }
            while (j >= 0) {
                if (T.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }
            if (i >= 0 && j >= 0) {
                if (S.charAt(i) != T.charAt(j)) {
                    return false;
                }
            } else {
                if (i >= 0 || j >= 0) {
                    return false;
                }
            }
            i--;
            j--;
        }
        return true;
    }


    public String realString(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && c == '#') {
                stack.pollLast();
            } else if (c != '#') {
                stack.addLast(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            sb.append(stack.pollFirst());
        }
        return sb.toString();
    }
}
