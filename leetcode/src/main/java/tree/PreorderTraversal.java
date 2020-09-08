package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName: PreorderTraversal
 * @Auther: MeerkatX
 * @Date: 2020-09-08 17:47
 * @Description: 144. 二叉树的前序遍历
 * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 */
public class PreorderTraversal {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        //ArrayList 按照原来的顺序遍历 根 左 右
        while (!stack.isEmpty()) {
            TreeNode r = stack.pop();
            ans.add(r.val);
            // stack 模拟，那么根之后 先访问 左，所以先添加 右 到stack中
            if (r.right != null) stack.push(r.right);
            if (r.left != null) stack.push(r.left);
        }
        return ans;
    }
}
