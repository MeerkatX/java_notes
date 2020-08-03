package tree;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/16
 * @Description: 297. 二叉树的序列化与反序列化
 */
public class Codec {
    public static void main(String[] args) {

        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(5);
        TreeNode n4 = new TreeNode(3);
        TreeNode n5 = new TreeNode(4);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(5);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n3.left = n5;
        n3.right = n6;

        Codec codec = new Codec();
        TreeNode node = codec.deserialize(codec.serialize(n1));
    }


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        deque.addLast(root);
        while (!deque.isEmpty()) {
            TreeNode treeNode = deque.pollFirst();
            sb.append(treeNode == null ? "null," : treeNode.val + ",");
            if (treeNode != null) {
                deque.addLast(treeNode.left);
                deque.addLast(treeNode.right);
            }
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodes = data.substring(1, data.length()-1).split(",");
        TreeNode root = nodes[0].equals("null")?null:new TreeNode(Integer.valueOf(nodes[0]));
        Queue<TreeNode> parents = new LinkedList();
        TreeNode parent = root;
        boolean isLeft = true;
        for(int i = 1; i < nodes.length; i++){
            TreeNode cur = nodes[i].equals("null")?null:new TreeNode(Integer.valueOf(nodes[i]));
            if(isLeft){
                parent.left = cur;
            }else{
                parent.right = cur;
            }
            if(cur != null){
                parents.add(cur);
            }
            isLeft = !isLeft;
            if(isLeft){
                parent = parents.peek();
                parents.poll();
            }
        }
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
