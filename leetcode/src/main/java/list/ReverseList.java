package list;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/26
 * @Description: 翻转列表 递归
 * https://leetcode-cn.com/problems/reverse-linked-list/solution/fan-zhuan-lian-biao-by-leetcode/
 */
public class ReverseList {

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

    public ListNode reverseList2(ListNode head) {
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
}
