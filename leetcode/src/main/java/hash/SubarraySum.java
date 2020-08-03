package hash;

import java.util.HashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/26
 * @Description: 和为K的子数组
 */
public class SubarraySum {

    public int subarraySum(int[] nums, int k) {
        int count = 0, pre = 0;
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (mp.containsKey(pre - k))
                count += mp.get(pre - k);
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }


}
