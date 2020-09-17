package tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @ClassName: InorderTraversal
 * @Auther: MeerkatX
 * @Date: 2020-09-14 09:14
 * @Description: 94. 二叉树的中序遍历
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 */
public class InorderTraversal {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        treeNode.right=treeNode2;
        treeNode2.left = treeNode3;
        List<Integer> a = new InorderTraversal().inorderTraversal(treeNode);
        System.out.println();
    }


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root!=null || !stack.isEmpty()){
            //中序遍历需要while循环至最左，之后再做运算
            while (root != null){
                stack.addLast(root);
                root = root.left;
            }
            root = stack.pollLast();
            ans.add(root.val);
            root = root.right;
        }
        return ans;
    }
}
