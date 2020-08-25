/**
 * @ClassName: RepeatedSubstringPattern
 * @Auther: MeerkatX
 * @Date: 2020-08-24 12:04
 * @Description: 459. 重复的子字符串 和 KMP 子字符串查找算法
 */
public class RepeatedSubstringPattern {

    public boolean repeatedSubstringPattern(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }

    public static void main(String[] args) {

    }

    public int search(String text) {
        return 0;
    }
}
