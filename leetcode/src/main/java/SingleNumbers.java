

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/28
 * @Description:
 */
public class SingleNumbers {

    public int[] singleNumbers(int[] nums) {
        //排序 快排 时间复杂度 O(NlogN) 不满足题目要求
        Arrays.sort(nums);
        int[] result = new int[2];
        int j = 0;
        if (nums[1] != nums[0])
            result[j++] = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] != nums[i - 1] && nums[i + 1] != nums[i])
                result[j++] = nums[i];
        }
        if (nums[nums.length - 1] != nums[nums.length - 2])
            result[j++] = nums[nums.length - 1];
        return result;
    }

    public int[] singleNumbers2(int[] nums) {
        //异或
        int[] result = new int[2];
        int sum = 0;
        //异或运算
        // a^a=0
        // a^0=a
        // 所以最后得到的就是 a^b 两个不同的数之间的异或结果
        for (int i : nums) {
            sum ^= i;
        }
        int index = 0;
        while ((sum & 1) == 0) {
            sum=sum>>1;
            index++;
        }
        //分两组
        for (int i:nums){
            //因为得到了两者不同的那位，就根据这位分开，其他数也可能进入到，但是因为是两两配对的所以
            //result异或 i 就过滤掉了相同的数，只留下唯一的那个数
            if (((i>>index) & 1) == 0){
                result[0]^=i;
            }else {
                //同上
                result[1]^=i;
            }
        }
        return result;
    }
}
