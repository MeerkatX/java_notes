package tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/18
 * @Description:
 */
public class RecoverFromPreorder {

    public static void main(String[] args) {
        new RecoverFromPreorder().recoverFromPreorder("1-2--3--4-5--6--7");
    }

    public TreeNode recoverFromPreorder(String S) {
        // LinkedList中 pop 为 removeFirst push 为 addFirst 即头插法
        Deque<TreeNode> stack = new LinkedList<>(); //利用栈存根节点
        int pos = 0;
        while (pos < S.length()) {
            int level = 0;
            while (S.charAt(pos) == '-') {
                level++;
                pos++;
            } //遍历查找如果前面有n个'-'说明它在第n层
            int digit = 0;
            while (pos < S.length() && Character.isDigit(S.charAt(pos))) {
                //知道层数后，紧接着是数字，可能大于10,100所以需要转为数字，直到遇到下一个'-'
                digit = digit * 10 + S.charAt(pos++) - '0';
            }
            TreeNode node = new TreeNode(digit);//生成节点
            if (level == stack.size()) {
                //如果层数刚好等于栈内大小，说明刚好是当前节点的根节点
                // 如果节点只有一个子节点，那么保证该子节点为左子节点。（题目要求）
                if (!stack.isEmpty()) stack.peek().left = node;
            } else {
                //如果层数不同，说明已经到右子树了（回溯到当前节点的根节点）
                while (level != stack.size()) stack.pop();
                stack.peek().right = node;
            }
            stack.push(node);//根据根左右，每次遍历都把下次可能为根的节点加上
        }
//        while (stack.size() > 1) stack.pop();//最后头节点肯定是最终的根节点
        return stack.pollLast();
    }


    ////////////////////////////////////////递归 dfs来做

    int cur = 0, curD = 0;

    public TreeNode recoverFromPreorder2(String S) {
        char[] nodes = S.toCharArray();
        return dfs(0, nodes);
    }

    public TreeNode dfs(int depth, char[] nodes) {
        int val = 0;
        for (; cur < nodes.length && nodes[cur] != '-'; cur++)
            val = val * 10 + nodes[cur] - '0';
        curD = 0;
        for (; cur < nodes.length && nodes[cur] == '-'; cur++, curD++) ;
        TreeNode r = new TreeNode(val);
        //depth为当前深度 如果超过当前深度了，就继续往下递归
        if (curD > depth) r.left = dfs(curD, nodes);
        if (curD > depth) r.right = dfs(curD, nodes);
        return r;
    }
}
