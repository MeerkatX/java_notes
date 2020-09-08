package list;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @ClassName: MergeTwoLists
 * @Auther: MeerkatX
 * @Date: 2020-09-03 17:36
 * @Description: 合并两个链表
 * https://www.nowcoder.com/practice/a479a3f0c4554867b35356e0d57cf03d?tpId=190&rp=1&ru=%2Factivity%2Foj&qru=%2Fta%2Fjob-code-high-rd%2Fquestion-ranking
 */
public class MergeTwoLists {

    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->
            b-a
        );
        pq.add(1);
        pq.add(3);
        System.out.println(pq.poll());
    }


    /**
     * @param l1 ListNode类
     * @param l2 ListNode类
     * @return ListNode类
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // write code here

        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        p.next = p1 == null ? p2 : p1;
        return head.next;
    }
}
