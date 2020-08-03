package list;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/1
 * @Description:
 */
public class SortList {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(-1);
        ListNode l2 = new ListNode(0);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        new SortList().sortList(l1);
    }

    public ListNode sortList(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode tail = head;
        int length = 0;
        while (tail != null) {
            tail = tail.next;
            length++;
        }
        tail = head;
        for (int i = 0; i < length / 2-1; i++) {
            tail = tail.next;
        }
        ListNode h1 = head;
        ListNode h2 = tail.next;
        tail.next = null;
        h1 = sortList(h1);
        h2 = sortList(h2);
        return mergeTwoLists(h1, h2);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode point = head;
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                point.next = l2;
                l2 = l2.next;
                point = point.next;
            } else if (l2 == null) {
                point.next = l1;
                l1 = l1.next;
                point = point.next;
            } else if (l1.val > l2.val) {
                point.next = l2;
                l2 = l2.next;
                point = point.next;
            } else {
                point.next = l1;
                l1 = l1.next;
                point = point.next;
            }
        }
        return head.next;
    }
}
