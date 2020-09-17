package tree;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: PostorderTraversal
 * @Auther: MeerkatX
 * @Date: 2020-09-08 17:41
 * @Description: 145. 二叉树的后序遍历
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
 */
public class PostorderTraversal {

    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) {
            return output;
        }

        stack.add(root);
        //迭代顺序刚好与访问顺序相反 也就是变成 根，右，左 然后 addFirst 通过头插
        //变为 左、右、根
        while (!stack.isEmpty()) {
            TreeNode node = stack.pollLast();
            output.addFirst(node.val);//头插
            //根 右 左的顺序，所以先往栈中加入左（这样出栈就是右先出）
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        return output;
    }
}
