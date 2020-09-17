package slidingwindow;

import java.util.HashSet;

/**
 * @ClassName: MaxLength
 * @Auther: MeerkatX
 * @Date: 2020-09-14 16:28
 * @Description:  找到字符串的最长无重复字符子串
 * 给定一个数组arr，返回arr的最长无的重复子串的长度(无重复指的是所有数字都不相同)。
 * https://www.nowcoder.com/practice/b56799ebfd684fb394bd315e89324fb4?tpId=188&&tqId=35297&rp=1&ru=/ta/job-code-high-week&qru=/ta/job-code-high-week/question-ranking
 */
public class MaxLength {
    /**
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public int maxLength(int[] arr) {
        // write code here
        int left = 0;
        int right = 0;
        int max = 0;
        HashSet<Integer> set = new HashSet<>();
        while (left < arr.length && right < arr.length) {
            int t = arr[right];
            if (set.contains(t)) {
                max = Math.max(right - left, max);
                while (left < arr.length) {
                    if (arr[left] != t) {
                        set.remove(arr[left]);
                        left += 1;
                    } else {
                        left += 1;
                        break;
                    }
                }
            } else {
                set.add(t);
            }
            right++;
        }
        return Math.max(right - left, max);
    }
}
