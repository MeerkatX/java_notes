package sort;

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/22
 * @Description:
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] nums = {1, 4, 5, 7, 8, 1, 3, 6, 2, 3, 7};
        QuickSort.sort(nums);
        Arrays.stream(nums).forEach(o -> {
            System.out.print(o + "  ");
        });
    }

    public static void sort(int[] nums) {
        quicksort(nums, 0, nums.length - 1);
    }

    public static void quicksort(int[] nums, int start, int end) {
        if (end <= start)
            return;
        int j = partition(nums, start, end);
        quicksort(nums, start, j - 1);
        quicksort(nums, j + 1, end);
    }

    public static int partition(int[] nums, int start, int end) {
        int temp = nums[start];
        int i = start + 1;
        int j = end;
        while (true) {
            while (nums[i] < temp && i <= end) {
                i++;
            }
            while (nums[j] >= temp) {
                j--;
            }
            //这个判断是可能存在 i 超过j 的情况，
            //i 始终应该在 j 的左侧 如果仅在主循环中while(i<j)是不够的，因为下面还会交换一次。
            //所以需要在交换前判断i 是否大于 j ，然后再交换
            if (i >= j)
                break;
            //如果i超过j那么交换将
            int swap = nums[i];
            nums[i] = nums[j];
            nums[j] = swap;
            //这里进行i++ j--会影响下面交换
        }
        nums[start] = nums[j];
        nums[j] = temp;
        return j;
    }

}
