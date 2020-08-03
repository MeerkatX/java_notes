package tree;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 *
 * 二叉树展开为链表
 */
public class Flatten {

    private TreeNode pre;

    public void flatten(TreeNode root) {
        if(root == null) return;
        if(root.left==null && root.right==null) return;
        pre = root;
        dfs(root);
    }

    public void dfs(TreeNode root){
        if(root == null ) return;
        TreeNode r = root.right;
        TreeNode l = root.left;

        pre.right = root;
        pre.left = null;

        pre = root;
        dfs(l);
        dfs(r);
    }
}
