package tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @ClassName: AverageOfLevels
 * @Auther: MeerkatX
 * @Date: 2020-09-12 14:10
 * @Description: 637. 二叉树的层平均值
 * https://leetcode-cn.com/problems/average-of-levels-in-binary-tree/
 */
public class AverageOfLevels {

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        if (root == null) return ans;

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addFirst(root);

        while (!deque.isEmpty()) {
            double sum = 0;
            double nums = deque.size();
            for (int i = 0; i < nums; i++) {
                TreeNode t = deque.pollFirst();
                sum += t.val;
                if (t.left != null) deque.addLast(t.left);
                if (t.right != null) deque.addLast(t.right);
            }
            ans.add((sum / nums));
        }
        return ans;
    }

}
