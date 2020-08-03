package bs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 * 162. 寻找峰值
 */
public class FindPeakElement {
    //另一只可以遍历

    public int findPeakElement(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }
    public int search(int[] nums, int l, int r) {
        if (l == r)
            return l;
        int mid = (l + r) / 2; //获取中间
        //如果中间的数比中间后一个大，说明峰值在前面  l -> mid 间
        if (nums[mid] > nums[mid + 1])
            return search(nums, l, mid);
        return search(nums, mid + 1, r);//否则就说明在后面 mid+1 -> r 间
    }
}
