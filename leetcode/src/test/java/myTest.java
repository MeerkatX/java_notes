import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/27
 * @Description:
 */
public class myTest {

    @Test
    public void test() {

//        String a = new String("abc");
//        String b = new String("abc");
//        HashSet<String> set = new HashSet<>();
//        set.add(a);
//        set.add(b);
//        System.out.println(set.size());
//        System.out.println(a==b);
//        System.out.println(a.hashCode()+" "+b.hashCode());
//
//        Scanner in = new Scanner(System.in);
//        int T = in.nextInt();

    }

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
        return Math.max(right - left + 1, max);
    }

    @Test
    public void test2() {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println(myStack.pop());
    }
}
