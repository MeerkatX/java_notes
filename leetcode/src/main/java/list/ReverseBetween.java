package list;

import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/22
 * @Description: 反转链表 II 97
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转
 */
public class ReverseBetween {

    //双指针头插法
    public ListNode reverseBetween2(ListNode head,int m,int n){
        ListNode h = new ListNode(0);
        h.next = head;
        ListNode p = h;
        for (int i = 0; i < m - 1; i++) p = p.next;
        ListNode q = p.next;
        for (int i = 0; i < n - m; i++) {
            ListNode t = q.next;
            q.next = q.next.next;

            t.next = p.next;
            p.next = t;
        }
        return h.next;
    }

    //不合规的非一趟扫描
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode h = new ListNode(0);
        h.next = head;
        ListNode p = h;
        for (int i = 0; i < m - 1; i++) p = p.next;
        ListNode q = p.next;
        for (int i = 0; i < n - m; i++) {
            q = q.next;
        }
        ListNode t1 = q != null ? q.next : null;
        ListNode t2 = p.next;
        if (q != null) q.next = null;
        p.next = reverseList(p.next);
        t2.next = t1;
        return h.next;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }
}
