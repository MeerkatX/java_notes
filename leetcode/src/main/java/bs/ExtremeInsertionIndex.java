package bs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/26
 * @Description:
 */
public class ExtremeInsertionIndex {

    public static void main(String[] args) {
        new ExtremeInsertionIndex().searchRange2(new int[]{2,2},2);
    }

    public int[] searchRange2(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) return new int[]{-1, -1};
        int left = 0;
        int right = n - 1;
        int mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                break;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (nums[mid] == target) {
            int i = mid, j = mid;
            while (i >= 0 && nums[i] == target) i--;
            while (j <n && nums[j] == target) j++;
            return new int[]{i + 1, j - 1};
        } else {
            return new int[]{-1, -1};
        }
    }

    private int extremeInsertionIndex(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > target || (left && target == nums[mid])) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] targetRange = {-1, -1};

        int leftIdx = extremeInsertionIndex(nums, target, true);

        // assert that `leftIdx` is within the array bounds and that `target`
        // is actually in `nums`.
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums, target, false) - 1;

        return targetRange;
    }
}
