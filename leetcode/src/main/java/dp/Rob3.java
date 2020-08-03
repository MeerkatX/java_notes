package dp;

import tree.TreeNode;

import java.util.LinkedList;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/29
 * @Description:
 */
public class Rob3 {

    int rob(TreeNode root) {
        int[] res = dp(root);
        return Math.max(res[0], res[1]);
    }

    /* 返回一个大小为 2 的数组 arr
    arr[0] 表示不抢 root 的话，得到的最大钱数
    arr[1] 表示抢 root 的话，得到的最大钱数 */
    int[] dp(TreeNode root) {
        if (root == null)
            return new int[]{0, 0};
        int[] left = dp(root.left);
        int[] right = dp(root.right);
        // 抢，下家就不能抢了
        int rob = root.val + left[0] + right[0];
        // 不抢，下家可抢可不抢，取决于收益大小
        int not_rob = Math.max(left[0], left[1])
                + Math.max(right[0], right[1]);

        return new int[]{not_rob, rob};
    }


//////////////////////////错误尝试
    private int sum = 0;
    private int pre = 0;
    private int after =0;
    private int max =0;

    public int rob3(TreeNode root) {
        pre = 0;
        after = root.val;
        dfs(root);
        return max;
    }

    public void dfs(TreeNode root) {
        if (root == null) {
            max = Math.max(max,after);
        }
        sum = Math.max(root.val + pre, after);
        if (root.left != null) {
            dfs(root.left);
        }
        if (root.right != null) {
            dfs(root.right);
        }
    }
}
