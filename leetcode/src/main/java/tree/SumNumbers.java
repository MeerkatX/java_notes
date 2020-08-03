package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/5
 * @Description:
 */
public class SumNumbers {

    public static void main(String[] args) {

    }

    private int sum = 0;

    public int sumNumbers(TreeNode root) {
        sumNumbersHelper(root, 0);
        return sum;
    }

    public void sumNumbersHelper(TreeNode root, int value) {
        if (root.left == null && root.right == null) {
            sum+=(value * 10 + root.val);
            return;
        }
        value = value * 10 + root.val;
        if (root.left != null) {
            sumNumbersHelper(root.left, value);
        }
        if (root.right != null) {
            sumNumbersHelper(root.right, value);
        }
    }
}
