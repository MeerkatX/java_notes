package bs;

import java.util.HashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/3
 * @Description:
 */
public class TwoSum {
    public static void main(String[] args) {
        new TwoSum().twoSum(new int[]{2, 7, 11, 15}, 9);
    }

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int mid = (right + left) / 2;
            if (numbers[mid] > target) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        HashMap<Integer, Integer> set = new HashMap<Integer, Integer>();
        for (int i = 0; i <= left; i++) {
            int another = target - numbers[i];
            if (set.containsKey(another))
                return new int[]{set.get(another), i};
            set.put(numbers[i], i);
        }
        return new int[0];
    }
}
