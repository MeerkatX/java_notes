package tree;

import sun.reflect.generics.tree.Tree;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/23
 * @Description: 根据前序，中序重建二叉树
 */
public class BuildTree {

    public static void main(String[] args) {
        BuildTree buildTree = new BuildTree();
        TreeNode treeNode = buildTree.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        System.out.println();
    }

    private int r = 0; //标记目前到 哪个根 了

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, inorder, 0, preorder.length);
    }

    private TreeNode build(int[] preorder,int[] inorder, int i1, int i2) {
        if (i2 == i1) return null;
        TreeNode root = new TreeNode(preorder[this.r]); //前序得到根节点
        int i = i1;
        while(inorder[i] != preorder[this.r]) i++;
        //递归 对重建左右子数
        this.r++;
        root.left = build(preorder, inorder, i1, i);
        root.right = build(preorder, inorder, i + 1, i2);
        return root;
    }
}
