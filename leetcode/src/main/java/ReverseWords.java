import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 */
public class ReverseWords {
    public static void main(String[] args) {
        new ReverseWords().reverseWords("  hello world!");
    }

    public String reverseWords(String s) {
        int n = 0;
        s = s.trim();
        StringBuilder ans = new StringBuilder();
        while (n < s.length()) {
            StringBuilder oneword = new StringBuilder();
            int p = n;
            boolean flag = false;
            while (p<s.length()){
                if (s.charAt(p)!=' '){
                    oneword.append(s.charAt(p));
                    flag = true;
                }else {
                    if (flag) break;
                }
                p++;
            }
            ans.insert(0," "+oneword);
            n=p;
        }
        return ans.toString().trim();
    }

    public String reverseWords2(String s) {
        int left = 0, right = s.length() - 1;
        // 去掉字符串开头的空白字符
        while (left <= right && s.charAt(left) == ' ') ++left;

        // 去掉字符串末尾的空白字符
        while (left <= right && s.charAt(right) == ' ') --right;

        Deque<String> d = new ArrayDeque();
        StringBuilder word = new StringBuilder();

        while (left <= right) {
            char c = s.charAt(left);
            if ((word.length() != 0) && (c == ' ')) {
                // 将单词 push 到队列的头部
                d.offerFirst(word.toString());
                word.setLength(0);
            } else if (c != ' ') {
                word.append(c);
            }
            ++left;
        }
        d.offerFirst(word.toString());

        return String.join(" ", d);
    }

}
