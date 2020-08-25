package tree;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public class RedBlackTree {

    private Node root;

    private final static boolean RED = true;
    private final static boolean BLACK = false;

    private class Node {
        int val;//值
        Node left, right;//左右子树
        int N;//结点数
        boolean color;//true为红色

        Node(int val, int N, boolean color) {
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    //旋转
    //左旋
    //其中h可能是红色或黑色
    //实际要做的就是把 h 的右节点 变成 父节点
    Node rotateLeft(Node h) {
        Node x = h.right;//获取右节点（红色）
        h.right = x.left;//将 x 的 左节点 挂在 右节点 上，2-3节点的中间节点
        x.left = h;
        x.color = h.color;//这里可能导致两节点都是红色
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    //右旋
    //同理
    Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    //转换一个结点的两个红节点
    void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public void put(int val){
        root = put(root,val);
        root.color = BLACK;
    }

    private Node put(Node h,int val){
        if(h == null) return new Node(val,1,RED);

        int cmp = val - h.val;
        if (cmp<0) h.left = put(h.left,val);
        else if(cmp>0) h.right = put(h.right,val);
        else h.val = val;

        //左转、右转、变色
        //右转、变色
        //变色

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);//保持红链接为左连接
        if (isRed(h.left)&& isRed(h.left.left)) h=rotateRight(h);//如果前一步，或者传入的h为两个红结点相连，右转
        if (isRed(h.left)&&isRed(h.right)) flipColors(h);//如果h的左右都是红结点，就变黑。前一步操作可能导致这种情况

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public static void main(String[] args) {


//        String[] tes = "Abbbbbbb cd eee aaaaa".toLowerCase().split(" ");
//        Arrays.sort(tes, (one, two) -> {
//            return one.length() - two.length();
//        });
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < tes.length - 1; i++) {
//            if (i == 0) tes[0] = Character.toUpperCase(tes[i].charAt(0) - 32) + tes[0].substring(1);
//            sb.append(tes[i] + " ");
//        }
//        sb.append(tes[tes.length - 1]);
//        System.out.println(sb.toString());

        RedBlackTree root = new RedBlackTree();
        root.put(1);
        root.put(4);
        root.put(5);
        root.put(3);
        root.put(9);
        root.put(7);
    }

}
