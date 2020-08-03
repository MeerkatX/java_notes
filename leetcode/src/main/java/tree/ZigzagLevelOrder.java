package tree;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/4
 * @Description: 103. 二叉树的锯齿形层次遍历
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 */
public class ZigzagLevelOrder {

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<List<Integer>> ans = new ArrayList<>();
        boolean flag = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            Deque<Integer> deque = new ArrayDeque<>();
            for (int i = 0; i < size; i++) {
                TreeNode r = queue.poll();
                if (flag) deque.add(r.val);//通过双端队列完成翻转 一个尾插一个头插
                else deque.addFirst(r.val);
                if (r.left != null) queue.offer(r.left);
                if (r.right != null) queue.offer(r.right);
            }
            ans.add(new ArrayList(deque));
            flag = !flag;
        }
        return ans;
    }
}
