package list;

/**
 * @ClassName: HasCycle
 * @Auther: MeerkatX
 * @Date: 2020-09-03 16:23
 * @Description:  141. 环形链表
 * https://leetcode-cn.com/problems/linked-list-cycle/
 */
public class HasCycle {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4= new ListNode(4);
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
//        l4.next = l5;
//        l5.next = l6;
//        l6.next = l3;

        System.out.println(new HasCycle().hasCycle(l1));
    }

    public boolean hasCycle(ListNode head) {
        if(head == null) return false;
        if(head.next == null) return false;
        ListNode fast = head.next.next;
        ListNode slow = head;
        while(slow != fast){
            if (fast == null || fast.next == null) return false;
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }
}
