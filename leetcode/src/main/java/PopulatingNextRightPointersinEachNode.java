import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
public class PopulatingNextRightPointersinEachNode {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;


        new PopulatingNextRightPointersinEachNode().connect(n1);
    }

    public Node connect(Node root) {
        LinkedList<Node> queue = new LinkedList<>();
        int i = 1;
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.pollFirst();

            if (node.left != null) queue.addLast(node.left);
            if (node.right != null) queue.addLast(node.right);

            for (int j = 1; j < i; j++) {
                Node n = queue.pollFirst();

                if (n.left != null) queue.addLast(n.left);
                if (n.right != null) queue.addLast(n.right);

                node.next = n;
                node = n;
            }
            i = i * 2;
        }
        return root;
    }

    ///////////////////////////////递归///////////////////////////////
    /*
    每个左子树的next就是右子树
    每个node右子树的next就是node.next的左子树(如果有的话，否则右子树)
     */

    public Node connect2(Node root) {
        if (root == null || (root.right == null && root.left == null)) {
            return root;
        }
        if (root.left != null && root.right != null) {
            root.left.next = root.right;
            root.right.next = getNextNoNullChild(root);
        }
        if (root.left == null) {
            root.right.next = getNextNoNullChild(root);
        }
        if (root.right == null) {
            root.left.next = getNextNoNullChild(root);
        }

        //这里要注意：先递归右子树，否则右子树根节点next关系没建立好，左子树到右子树子节点无法正确挂载
        root.right = connect2(root.right);
        root.left = connect2(root.left);

        return root;
    }

    /**
     * 一路向右找到有子节点的根节点
     */
    private static Node getNextNoNullChild(Node root) {
        while (root.next != null) {
            if (root.next.left != null) {
                return root.next.left;
            }
            if (root.next.right != null) {
                return root.next.right;
            }
            root = root.next;
        }
        return null;
    }

}

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

}
