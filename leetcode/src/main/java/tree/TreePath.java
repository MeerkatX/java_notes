package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/24
 * @Description:
 */
public class TreePath {
    private static List<List<Integer>> ans = new ArrayList<>();

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(5);
        TreeNode n4 = new TreeNode(1);
//        TreeNode n5 = new TreeNode(2);
////        TreeNode n6 = new TreeNode(4);
////        TreeNode n7 = new TreeNode(5);
////        TreeNode n8 = new TreeNode(5);
////        TreeNode n9 = new TreeNode(2);
        root.left = n1;
        root.right = n2;
        n1.left = n3;
        n3.right = n4;

        List<Integer> path = new ArrayList<>(root.val);
        new TreePath().treepath(root, path);
        System.out.println();
    }

    public void treepath(TreeNode root, List<Integer> path) {

        if (root != null) path.add(root.val);
        if (root.left == null && root.right == null) {
            this.ans.add(new ArrayList<>(path));
            return;
        }

        if (root.left != null) {
            treepath(root.left, path);
            path.remove(path.size()-1);
        }

        if (root.right != null) {
            treepath(root.right, path);
            path.remove(path.size()-1);
        }

    }
}
