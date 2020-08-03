/**
 * @Auther: 徐少伟
 * @Date: 2020/6/4
 * @Description:
 */
public class ProductExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = nums[0];//左边过一遍
        for (int i = 1; i < n; i++) {
            left[i] = nums[i] * left[i - 1];
        }
        right[n - 1] = nums[n - 1];//右边过一遍
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i];
        }
        int[] ans = new int[n];
        ans[0] = right[1];
        ans[n - 1] = left[n - 2];
        //中间过一遍
        for (int i = 1; i < n - 1; i++) {
            ans[i] = left[i - 1] * right[i + 1];
        }
        return ans;
    }
}
