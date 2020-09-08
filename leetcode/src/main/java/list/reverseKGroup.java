package list;

import java.util.HashSet;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/16
 * @Description: 25. K 个一组翻转链表
 *
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 */
public class reverseKGroup {

    public static void main(String[] args) {

        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        ListNode l = new reverseKGroup().reverseKGroup(l1,3);
        while (l!=null){
            System.out.println(l.val);
            l=l.next;
        }
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode h = new ListNode(0);
        ListNode p = h;
        h.next = head;
        ListNode fast = head;
        ListNode slow = head;
        while(p.next!=null){
            for(int i = 0; i< k-1 && fast.next!=null;i++){
                fast = fast.next;
            }
            ListNode temp = fast.next;
            if(temp == null) break;
            fast.next = null;
            ListNode reverse = reverseList(slow);
            slow.next = temp;
            p.next = reverse;
            p = slow;
            fast = slow.next;
            slow = slow.next;
        }
        return h.next;



    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = reverseList(head.next);
        head.next.next = head;//重点是这里
        // a->b->c->d的话  c->d ，这样 c .next 为 d ；d .next 应当指向 c
        // 所以有 c.next.next = c

        //假设 a->b->c->d<-e 这样，c.next.next = c
        head.next = null;//不指向null会导致列表循环
        return p;
    }
}
