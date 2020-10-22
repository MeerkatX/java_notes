package array;

import java.util.Objects;

/**
 * @ClassName: Rotate
 * @Auther: MeerkatX
 * @Date: 2020-10-14 11:03
 * @Description:
 */
public class Rotate {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        new Rotate().rotate(nums, 2);
        for (int p : nums) {
            System.out.println(p);
        }
    }

    public void rotateError(int[] nums, int k) {
        int length = nums.length;
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int first = nums[start];//针对相同元素无法判断
            int next = nums[start];
            int p = (k + start) % length;
            while (nums[p] != first) {
                int temp = nums[p];
                nums[p] = next;
                next = temp;
                p = (p + k) % length;
                count++;
            }
            nums[start] = next;
            count++;
        }

    }

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}
