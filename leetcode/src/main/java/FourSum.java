
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/22
 * @Description:
 *
 *
 */
public class FourSum {

    //类似ThreeSum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        for (int i = 0; i < nums.length - 4; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            for (int k = i + 1; k < nums.length - 3; k++) {
                //确保不重复，即k变了
                if (k > i + 1 && nums[k] == nums[k - 1])
                    continue;
                int left = k + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[k] + nums[left] + nums[right];
                    if (sum > target) {
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        re.add(Arrays.asList(nums[i], nums[k], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1])
                            left++;
                        while (left < right && nums[right] == nums[right - 1])
                            right--;
                        left++;
                        right--;
                    }
                }
            }
        }
        return re;
    }
}
