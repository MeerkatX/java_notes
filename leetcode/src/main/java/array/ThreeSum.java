package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/22
 * @Description: 15. 三数之和
 * 数组，双指针
 * https://leetcode-cn.com/problems/3sum/
 */
public class ThreeSum {
    public static void main(String[] args) {
        ThreeSum threeSum = new ThreeSum();
        threeSum.threeSum(new int[]{-1, 0, 1, 2, -1, -4}).stream().forEach(o -> {
            System.out.println(o + " ");
        });
    }


    public List<List<Integer>> threeSum(int[] nums) {
        //可能是经验：对于数组中找数的题，用双指针，多指针可能更好。
        //对于要去掉重复，累加等于一个数之类的题，先排序可能更好解
        //twoSum用映射可能更好 a[nums[i]] == n - nums[j]
        Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0)
                return re;
            if (i > 0 && nums[i] == nums[i - 1])//判断是否与前一个相同，去重复 -1 -1 -1 2 的形式
                continue;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                //排序之后，从i开始
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    //如果加起来大就往前
                    right--;
                } else if (sum < 0) {
                    //如果加起来小就往后
                    left++;
                } else {
                    //等于0就添加到结果集中
                    List<Integer> t = new ArrayList<>();
                    t.add(nums[i]);
                    t.add(nums[left]);
                    t.add(nums[right]);
                    re.add(t);
                    //应该是while ( left < right && nums[left] == nums[left + 1]) 不然可能会越界
                    //这一步是去掉重复元素
                    while (left < right && nums[left] == nums[left + 1] ) left++;
                    while (right > left && nums[right] == nums[right - 1] ) right--;
                    //在去掉重复元素之后，需要继续对比下一个
                    left++;
                    right--;
                }
            }
        }
        return re;
    }
}
