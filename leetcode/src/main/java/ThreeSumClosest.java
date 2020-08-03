
import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/22
 * @Description:
 */
public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int min = 9999999;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                min = Math.min(Math.abs(sum - target), min);
                if (sum > target) {
                    right--;
                } else if (sum < target) {
                    left++;
                }
            }
        }
        return min;
    }
}
