

import java.util.HashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/20
 * @Description:
 */
public class SubarraySum {


    public static void main(String[] args) {
        System.out.println(new SubarraySum().subarraySum(new int[]{3,5,2,-2,4,1},8));
    }

    int subarraySum(int[] nums, int k) {
        int n = nums.length;
        // map：前缀和 -> 该前缀和出现的次数
        HashMap<Integer, Integer>
                preSum = new HashMap<>();
        // base case
        preSum.put(0, 1);

        int ans = 0, sum0_i = 0;
        for (int i = 0; i < n; i++) {
            sum0_i += nums[i];
            // 这是我们想找的前缀和 nums[0..j]
            int sum0_j = sum0_i - k;
            // 如果前面有这个前缀和，则直接更新答案
            if (preSum.containsKey(sum0_j))
                ans += preSum.get(sum0_j);
            // 把前缀和 nums[0..i] 加入并记录出现次数
            preSum.put(sum0_i,
                    preSum.getOrDefault(sum0_i, 0) + 1);
        }
        return ans;
    }

}
