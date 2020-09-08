package list;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/25
 * @Description: 147. 对链表进行插入排序
 * https://leetcode-cn.com/problems/insertion-sort-list/
 */
public class InsertionSortList {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(4);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(5);
        l1.next.next.next = new ListNode(3);
        l1.next.next.next.next = new ListNode(1);
        l1 = new InsertionSortList().insertionSortList(l1);
        System.out.println();

    }

    public ListNode insertionSortList(ListNode head) {

        ListNode point = head.next;
        head.next = null;
        while (point != null) {
            ListNode h = head;
            ListNode pre = h;
            //插入位置
            while (h != null && h.val <= point.val) {
                pre = h;
                h = h.next;
            }
            ListNode t = point.next;
            if (pre == h) {
                point.next = head;
                head = point;
            } else {
                pre.next = point;
                point.next = h;
            }
            point = t;
        }
        return head;
    }
}
