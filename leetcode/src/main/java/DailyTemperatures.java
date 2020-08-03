import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/11
 * @Description: 739. 每日温度 单调栈
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        Arrays.stream(new DailyTemperatures().dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})).forEach(System.out::println);
    }


    public int[] dailyTemperatures(int[] T) {

        if (T.length == 0) return new int[0];


        Deque<int[]> stack = new ArrayDeque<>();//直接存索引值就行了，对比的话 T[stack.peekLast()]即可
        stack.addLast(new int[]{Integer.MAX_VALUE, 0});

        int[] ans = new int[T.length];

        for (int i = 0; i < T.length; i++) {
            if (stack.isEmpty()) stack.addLast(new int[]{T[i], i});
            else {
                while (!stack.isEmpty()) {
                    int[] v = stack.peekLast();
                    if (v[0] < T[i]) {
                        stack.pollLast();
                        ans[v[1]] = i - v[1];
                    } else {
                        stack.addLast(new int[]{T[i], i});
                        break;
                    }
                }
            }
        }
        return ans;
    }
}
