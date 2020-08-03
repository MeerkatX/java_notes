import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/10
 * @Description:
 */
public class SimplifyPath {

    public static void main(String[] args) {
        System.out.println(new SimplifyPath().simplifyPath("/a/.../../b/../c//.//"));
    }

    public String simplifyPath(String path) {
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast("/");
        String[] ps = path.split("/");
        for (int i = 1; i < ps.length; i++) {
            if (ps[i].equals("")) {
                if (!deque.peekLast().equals("/")) deque.addLast("/");
                continue;
            }

            if (ps[i].equals(".")) continue;

            if (ps[i].equals("..")) {
                if (deque.size() != 1) {
                    deque.pollLast();
                    deque.pollLast();
                }
                continue;
            }

            deque.addLast(ps[i]);
            deque.addLast("/");
        }
        StringBuilder ans = new StringBuilder();
        while (!deque.isEmpty()) {
            ans.append(deque.pollFirst());
        }
        return ans.length() == 1 ? ans.toString() : ans.substring(0, ans.length() - 1);
    }
}
