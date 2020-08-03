package hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/14
 * @Description: 前缀和
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int another = target - nums[i];
            if (hash.containsKey(another)) return new int[]{hash.get(another), i};
            hash.put(nums[i], i);
        }
        return new int[0];
    }

}
