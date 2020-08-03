

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/27
 * @Description:
 */
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        SearchInRotatedSortedArray searchInRotatedSortedArray = new SearchInRotatedSortedArray();
        int i = searchInRotatedSortedArray.search2(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
        System.out.println(i);
    }

    public int search(int[] nums, int target) {
        //先排序 不满足题目要求
        Arrays.sort(nums);
        return searchBinary(nums, 0, nums.length - 1, target);
    }


    public int searchBinary(int[] nums, int start, int end, int target) {
        if (start == end)
            if (nums[start] == target)
                return start;
            else
                return -1;
        int mid = start + (end - start) / 2;
        if (nums[mid] < target)
            return searchBinary(nums, mid + 1, end, target);
        else if (nums[mid] == target)
            return mid;
        else
            return searchBinary(nums, start, mid - 1, target);
    }


    public int search2(int[] nums, int target) {
        //对length < 3 会报错..
        int i = 0;
        while (i < nums.length && nums[i] < nums[++i]) {
        }
        if (nums[0] > target)
            return searchBinary(nums, i, nums.length - 1, target);
        else if (nums[0] < target)
            return searchBinary(nums, 0, i - 1, target);
        else
            return 0;
    }

    public int search3(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[start] <= nums[mid]) {
                //如果进入该判断， start 到 mid 有序
                //target在前半部分
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                // mid 到 end 有序
                if (target <= nums[end] && target > nums[mid]) {
                    //如果target在mid< target <end 就修改start 为 mid+1
                    start = mid + 1;
                } else {
                    //如果不在该区间内，可能是在 start < target <mid - 1
                    end = mid - 1;
                }
            }

        }
        return -1;
    }
}
