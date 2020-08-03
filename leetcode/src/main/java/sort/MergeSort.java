package sort;

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/17
 * @Description:
 */
public class MergeSort {

    private static int[] numsCpy;

    public static void merge(int[] nums, int low, int mid, int high) {
        int left = low;
        int right = mid + 1;
        for (int k = low; k <= high; k++) {
            try {
                numsCpy[k] = nums[k];
            } catch (Exception e) {
                System.out.println(k);
            }

        }
//        numsCpy = Arrays.copyOf(nums, nums.length);

        for (int k = low; k <= high; k++) {
            if (left > mid)
                //说明左侧归并完毕
                nums[k] = numsCpy[right++];
            else if (right > high)
                //说明右侧归并完毕
                nums[k] = numsCpy[left++];
            else if (numsCpy[left] >= numsCpy[right])
                //如果左侧大于右侧 等于右侧(小的)
                nums[k] = numsCpy[right++];
            else
                nums[k] = numsCpy[left++];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 4, 5, 7, 8, 1, 3, 6, 2, 3, 7};
        numsCpy = new int[nums.length];
//        MergeSort.sortTop(nums, 0, nums.length - 1);
        MergeSort.sortDown(nums);
        Arrays.stream(nums).forEach(o -> {
            System.out.print(o + "  ");
        });
    }

    //自顶向下
    public static void sortTop(int[] nums, int start, int end) {
        if (end <= start)
            return;
        int mid = start + (end - start) / 2;
        sortTop(nums, start, mid);
//        for (int k = start;k <= mid;k++)
//            System.out.print(nums[k]+" ");
//        System.out.println();
        sortTop(nums, mid + 1, end);
//        for (int k = mid+1;k <= end;k++)
//            System.out.print(nums[k]+" ");
//        System.out.println();
        merge(nums, start, mid, end);
//        for (int k = start;k <= end;k++)
//            System.out.print(nums[k]+" ");
//        System.out.println();
    }

    //自底向上
    public static void sortDown(int[] nums) {
        int N = nums.length;
        for (int i = 1; i < N; i *= 2) {
            for (int j = 0; j < N - i; j += i * 2) {
                merge(nums, j, j + i - 1, Math.min(N - 1, j + i * 2 - 1));
            }
        }
    }
}
