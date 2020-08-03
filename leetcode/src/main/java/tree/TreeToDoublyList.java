package tree;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/28
 * @Description: 二叉搜索树与双向链表
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。
 * 要求不能创建任何新的节点，只能调整树中节点指针的指向。
 */
public class TreeToDoublyList {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n2.left = n1;
        n2.right = n3;
        n4.left = n2;
        n4.right = n5;
        TreeToDoublyList treeToDoublyList = new TreeToDoublyList();
        treeToDoublyList.treeToDoublyList(n4);
    }

    public Node treeToDoublyList(Node root) {
        Node head = new Node(0);
        Node point = inorder(root, head);//inorder 中序遍历可以按顺序(从小到大)遍历二叉查找树 返回最后访问的节点
        point.right = head.right;
        head.right.left = point;
        return head.right;
    }

    public Node inorder(Node root, Node point) {
        if (root == null)
            return null;
        // 左
        Node temp = inorder(root.left, point);

        // 根
        point = temp == null ? point : temp; //因为是值传递所以需要大量的判断...
        point.right = root; // 连接下一个节点
        root.left = point; //双向链表需要将节点连接到上一个
        point = root; //往后走一步

        // 右
        temp = inorder(root.right, point);
        return temp == null ? point : temp;
    }

    /*
    java.lang.NullPointerException
    at line 117, TreeNodeSerializer.isValid
    at line 144, TreeNodeSerializer.serialize
    at line 186, __Driver__.main
     */

}

class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
}
