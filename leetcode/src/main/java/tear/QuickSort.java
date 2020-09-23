package tear;

import java.util.Arrays;

/**
 * @ClassName: QuickSort
 * @Auther: MeerkatX
 * @Date: 2020-09-18 16:31
 * @Description:
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 5, 1, 6, 55, 22, 5, 1, 56, 34};
        new QuickSort().binaryQuickSort(nums, 0, nums.length - 1);
        Arrays.stream(nums).forEach(System.out::println);
    }

    public void binaryQuickSort(int[] nums, int start, int end) {
        if (start >= end) return;
        int p = partition(nums, start, end);
        binaryQuickSort(nums, start, p - 1);
        binaryQuickSort(nums, p + 1, end);
    }

    public int partition(int[] nums, int start, int end) {
        int part = nums[start];
        int left = start + 1;
        int right = end;
        while (left <= right) {
            while (right >= 0 && nums[right] > part) right--;
            while (left < nums.length && nums[left] <= part) left++;
            if (left >= right) break;
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
        nums[start] = nums[right];
        nums[right] = part;
        return start;
    }


}
