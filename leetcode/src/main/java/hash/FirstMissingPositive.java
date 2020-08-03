package hash;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/27
 * @Description: 41. 缺失的第一个正数
 */
public class FirstMissingPositive {


    public int firstMissingPositive2(int[] nums) {
        int len = nums.length;
        //哈希 i 放到 nums[i-1] 的位置
        for (int i = 0; i < len; i++) {

            //while循环存在的意义： [ 3, 4, -1, 1] -> 第一次循环 i = 0  [ -1, 4, 3, 1] 其中 -1 不在范围内跳出while
            //第二次循环 i = 1 [ -1,1,3,4] 之后 1 仍然不在自己该在的位置那么 [ 1, -1, 3, 4] -1不在范围跳出
            //得到最终hash结果


            while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
                // 满足在指定范围内、并且没有放在正确的位置上，才交换
                // 例如：数值 3 应该放在索引 2 的位置上
                swap(nums, nums[i] - 1, i);
            }
        }

        // [1, -1, 3, 4]
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 都正确则返回数组长度 + 1
        return len + 1;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
/*
    作者：liweiwei1419
    链接：https://leetcode-cn.com/problems/first-missing-positive/solution/tong-pai-xu-python-dai-ma-by-liweiwei1419/
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
*/
    public int firstMissingPositive(int[] nums) {

        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }

        for (int i = 0; i < n; ++i) {
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
