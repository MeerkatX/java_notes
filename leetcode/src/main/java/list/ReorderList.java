package list;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/24
 * @Description: 143. 重排链表
 * https://leetcode-cn.com/problems/reorder-list/
 */
public class ReorderList {

    private HashMap<Integer, ListNode> map = new HashMap<>();

    public void reorderList(ListNode head) {
        int no = 0;
        ListNode p = head;
        while (p != null) {
            map.put(no++, p);
            p = p.next;
        }
        p = head;
        for (int i = 0; i < no / 2; i++) {
            p.next = map.get(no-i-1);
            p.next.next = map.get(i+1);
            map.get(i+1).next = null;
            p = p.next.next;
        }
    }

    public void reorderList2(ListNode head) {
        //分两半，后半部分翻转

        //挨个插入

    }

    public ListNode reverseList(ListNode head) {
        //非递归头插法 即一直不停将元素往列表头插入 a->b->c->d   b->a->c->d  c->b->a->d
        // 这样 所以需要辅助接点 t->a->b->c->d
        ListNode h = new ListNode(0);
        h.next = head;
        ListNode q = h.next;
        while (q != null && q.next != null) {
            ListNode t = q.next;
            q.next = q.next.next;

            t.next = h.next;
            h.next = t;
        }
        return h;
    }



    public void reorderList3(ListNode head) {
        //递归

        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        int len = 0;
        ListNode h = head;
        //求出节点数
        while (h != null) {
            len++;
            h = h.next;
        }

        reorderListHelper(head, len);
    }

    private ListNode reorderListHelper(ListNode head, int len) {
        if (len == 1) {
            ListNode outTail = head.next;
            head.next = null;
            return outTail;
        }
        if (len == 2) {
            ListNode outTail = head.next.next;
            head.next.next = null;
            return outTail;
        }
        //得到对应的尾节点，并且将头结点和尾节点之间的链表通过递归处理
        ListNode tail = reorderListHelper(head.next, len - 2);
        ListNode subHead = head.next;//中间链表的头结点
        head.next = tail;
        ListNode outTail = tail.next;  //上一层 head 对应的 tail
        tail.next = subHead;
        return outTail;
    }

}
