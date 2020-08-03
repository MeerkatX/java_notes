package list;

import java.util.HashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/8
 * @Description:
 */
public class CopyRandomList {
    public static void main(String[] args) {
        Node n1 = new Node(7);
        Node n2 = new Node(13);
        Node n3 = new Node(11);
        Node n4 = new Node(10);
        Node n5 = new Node(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n2.random = n1;
        n3.random = n5;


        new CopyRandomList().copyRandomList(n1);
    }

    public Node copyRandomList(Node head) {
        HashMap<Integer, Node> map = new HashMap<>();
        Node point = head;
        Node fhead = new Node(0);
        Node fp = fhead;
        int index = 0;
        while (point != null) {
            Node node = new Node(point.val);
            map.put(index++, node);
            fp.next = node;
            fp = node;
            point = point.next;
        }

        point = head;
        fp = fhead.next;
        while (point != null) {
            if (point.random != null) {
                Node h = head;
                index = 0;
                while (h != point.random) {
                    h = h.next;
                    index++;
                }
                fp.random = map.get(index);
            }
            point = point.next;
            fp = fp.next;
        }
        return fhead.next;
    }

    public Node copyRandomList2(Node head) {
        if (head == null) return null;
        HashMap<Node, Node> map = new HashMap<>();
        Node p = head;
        while (p != null) {
            map.put(p, new Node(p.val));
            p = p.next;
        }
        p = head;
        while (p != null) {
            map.get(p).next = map.get(p.next);
            map.get(p).random = map.get(p.random);
            p = p.next;
        }
        return map.get(head);
    }
}

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}