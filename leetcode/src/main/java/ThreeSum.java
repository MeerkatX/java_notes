import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/22
 * @Description:
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
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    List<Integer> t = new ArrayList<>();
                    t.add(nums[i]);
                    t.add(nums[left]);
                    t.add(nums[right]);
                    re.add(t);
                    //应该是while ( left < right && nums[left] == nums[left + 1]) 不然可能会越界
                    //即先判断左侧是否超过右侧
                    while (nums[left] == nums[left + 1] && left < right) left++;
                    while (nums[right] == nums[right - 1] && right > left) right--;
                    //在去掉重复元素之后，需要继续对比下一个
                    left++;
                    right--;
                }
            }
        }
        return re;
    }
}
