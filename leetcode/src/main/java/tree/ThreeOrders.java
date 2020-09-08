package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ThreeOrders
 * @Auther: MeerkatX
 * @Date: 2020-09-03 20:01
 * @Description: 分别按照二叉树先序，中序和后序打印所有的节点。
 * https://www.nowcoder.com/practice/a9fec6c46a684ad5a3abd4e365a9d362?tpId=190&tags=&title=&diffculty=0&judgeStatus=0&rp=1
 */
public class ThreeOrders {
    /**
     * @param root TreeNode类 the root of binary tree
     * @return int整型二维数组
     */
    public int[][] threeOrders(TreeNode root) {
        // write code here
        List<Integer> pre = preOrder(root, new ArrayList<Integer>());
        List<Integer> mid = midOrder(root, new ArrayList<Integer>());
        List<Integer> last = lastOrder(root, new ArrayList<Integer>());
        int[][] res = new int[3][pre.size()];
        for (int i = 0; i < pre.size(); i++) {
            res[0][i] = pre.get(i);
            res[1][i] = mid.get(i);
            res[2][i] = last.get(i);
        }
        return res;
    }

    public List<Integer> preOrder(TreeNode root, List<Integer> res) {
        if (root == null) return res;
        res.add(root.val);
        if (root.left != null) preOrder(root.left, res);
        if (root.right != null) preOrder(root.right, res);
        return res;
    }

    public List<Integer> midOrder(TreeNode root, List<Integer> res) {
        if (root == null) return res;
        if (root.left != null) midOrder(root.left, res);
        res.add(root.val);
        if (root.right != null) midOrder(root.right, res);
        return res;
    }

    public List<Integer> lastOrder(TreeNode root, List<Integer> res) {
        if (root == null) return res;
        if (root.left != null) lastOrder(root.left, res);
        if (root.right != null) lastOrder(root.right, res);
        res.add(root.val);
        return res;
    }
}
