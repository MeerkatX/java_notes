/**
 * @Auther: 徐少伟
 * @Date: 2020/6/1
 * @Description:
 */
public class RemoveDuplicates {

    public int removeDuplicates(int[] nums) {
        int p = 0;
        int f = 1;
        int count = 1;
        for (; f < nums.length; f++) {
            if (nums[f] == nums[p] && count < 2) {
                nums[++p] = nums[f];
                count++;
            }
            if (nums[f] != nums[p]) {
                nums[++p] = nums[f];
                count = 1;
            }
        }
        return p + 1;
    }

}
