package tree;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/4
 * @Description:
 */
public class SortedArrayToBST {
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTHelper(0, nums.length, nums);
    }

    public TreeNode sortedArrayToBSTHelper(int left, int right, int[] nums) {
        if (left >= right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBSTHelper(left, mid, nums);
        root.right = sortedArrayToBSTHelper(mid + 1, right, nums);
        return root;
    }
}
